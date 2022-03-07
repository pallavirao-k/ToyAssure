package com.increff.assure.dto;
import com.increff.assure.pojo.ChannelListingPojo;
import com.increff.assure.pojo.ChannelPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.ChannelData;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ChannelForm;
import com.increff.commons.Form.ChannelListingForm;
import com.increff.commons.Form.UploadChannelListingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.increff.assure.util.NormalizeUtil.normalize;
import static com.increff.commons.Util.ConvertUtil.convert;


@Service
public class ChannelDto extends AbstractDto {
    @Autowired
    private ChannelService service;
    @Autowired
    private PartyService partyService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProductService productService;

    public void add(ChannelForm form) throws ApiException {
        formValidation(form);
        checkName(form);
        service.addChannel(convert(form, ChannelPojo.class));
    }

    public void addChannelListings(UploadChannelListingForm uploadChannelListingForm) throws ApiException {
       validationsAndChecks(uploadChannelListingForm);
        List<ChannelListingPojo> channelListingPojoList = convertToCLPojo(uploadChannelListingForm);
        service.addChannelListing(channelListingPojoList);

    }

    public ChannelData getChannel(Long id) throws ApiException {
        ChannelPojo cp = service.getChannel(id);
        return convert(cp, ChannelData.class);
    }

    public List<ChannelData> getAllChannels(){
        List<ChannelPojo> list = service.getAllChannels();
        return list.stream().map(x->convert(x,ChannelData.class)).collect(Collectors.toList());
    }


    private void checkName(ChannelForm form) throws ApiException {

        if(form.getChannelName().trim().isEmpty()){
            throw new ApiException("Channel name must not be empty");
        }
    }

    private Long checkClient(String clientName) throws ApiException {
        return partyService.getCheckClientByName(clientName).getPartyId();
    }

    private Long checkChannel(String channelName) throws ApiException {
        return channelService.getCheckChannelByName(channelName).getId();
    }

    private List<ChannelListingPojo> convertToCLPojo(UploadChannelListingForm uploadChannelListingForm) throws ApiException {
        List<ChannelListingPojo> channelListingPojoList = new ArrayList<>();
        for(ChannelListingForm form : uploadChannelListingForm.getFormList()){
            ChannelListingPojo cp = convert(form, ChannelListingPojo.class);
            cp.setClientId(checkClient(uploadChannelListingForm.getClientName()));
            cp.setChannelId(checkChannel(uploadChannelListingForm.getChannelName()));
            cp.setGlobalSkuId(getProduct(checkClient(uploadChannelListingForm.getClientName()),form.getClientSkuId()).getGlobalSkuId());
        }
        return channelListingPojoList;
    }

    private ProductPojo getProduct(Long clientId, String clientSkuId){
        return productService.getByClientSkuIdAndClientId(clientSkuId, clientId);
    }

    private void validateClientSkuId(List<ChannelListingForm> formList ) throws ApiException {
        Long index=1L;
        List<Long> indexes = new ArrayList<>();
        Set<String> clientSkuIds = new HashSet<>();
        List<Long> indexes2 = new ArrayList<>();
        for(ChannelListingForm form: formList){
            if(form.getClientSkuId().trim().isEmpty()){
                indexes.add(index);
            }
            else{
                if(!clientSkuIds.contains(form.getClientSkuId())){
                    clientSkuIds.add(form.getClientSkuId());
                }
                else{
                    indexes2.add(index);
                }
            }
            index++;
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) are blank at indexes: ", indexes));
        }
        if(indexes2.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) are being repeated at indexes: ", indexes2));
        }

    }

    private void validateChannelSkuId(List<ChannelListingForm> formList) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Set<String> channelSkuIds = new HashSet<>();
        List<Long> indexes2 = new ArrayList<>();
        Long index=1L;
        for(ChannelListingForm form: formList){
            if(form.getChannelSkuId().trim().isEmpty()){
                indexes.add(index);
            }
            else{
                if(!channelSkuIds.contains(form.getClientSkuId())){
                    channelSkuIds.add(form.getClientSkuId());
                }
                else{
                    indexes2.add(index);
                }
            }
            index++;
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Channel SKU ID(s) rows are blank at indexes: ", indexes));
        }
        if(indexes2.size()>0){
            throw new ApiException(ErrorData.convert("Channel SKU ID(s) are being repeated at indexes: ", indexes2));
        }


    }

    private void checkClientSkuId(Long clientId, List<ChannelListingForm> formList) throws ApiException {

        List<String> initialClientSkuIds = formList.stream().map(ChannelListingForm::getClientSkuId).collect(Collectors.toList());
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, initialClientSkuIds);
        List<String> finalClientSkuIds = pojoList.stream().map(ProductPojo::getClientSkuId).collect(Collectors.toList());
        List<String> differences = initialClientSkuIds.stream().filter(element -> !finalClientSkuIds.contains(element)).collect(Collectors.toList());

        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("These Client SKU ID(s) don't exist", differences));
        }

    }

    private void checkCombinationUnique(Long clientId, Long channelId, List<ChannelListingForm> formList) throws ApiException {
        List<String> initialChannelSkuIds = formList.stream().map(ChannelListingForm::getChannelSkuId).collect(Collectors.toList());
        List<ChannelListingPojo> pojoList = service.getByClientIdChannelIdChannelSkuId(clientId,channelId,initialChannelSkuIds);
        List<String> finalChannelSkuIds = pojoList.stream().map(ChannelListingPojo::getChannelSkuId).collect(Collectors.toList());
        if(finalChannelSkuIds.size()>0){
            throw new ApiException(ErrorData.convert("Combination of ClientId: "+clientId+", ChannelId: "+channelId+" and these Channel SKU ID(s) already exist", finalChannelSkuIds));
        }
    }

    private void validationsAndChecks(UploadChannelListingForm uploadChannelListingForm) throws ApiException {
        formValidation(uploadChannelListingForm);
        Long clientId = checkClient(uploadChannelListingForm.getClientName());
        Long channelId = checkChannel(uploadChannelListingForm.getChannelName());
        validateClientSkuId(uploadChannelListingForm.getFormList());
        validateChannelSkuId(uploadChannelListingForm.getFormList());
        checkClientSkuId(clientId, uploadChannelListingForm.getFormList());
        checkCombinationUnique(clientId, channelId, uploadChannelListingForm.getFormList());
    }


}
