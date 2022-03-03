package com.increff.assure.dto;

import com.increff.assure.pojo.ChannelListingPojo;
;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.ChannelListingService;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;

import com.increff.commons.Data.ChannelListingData;
import com.increff.commons.Exception.ApiException;

import com.increff.commons.Form.ChannelListingForm;
import com.increff.commons.Form.UploadChannelListingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.increff.commons.Util.ConvertUtil.convert;

@Service
public class ChannelListingDto extends AbstractDto {

    @Autowired
    private ChannelListingService service;
    @Autowired
    private PartyService partyService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private ProductService productService;

    public void add(UploadChannelListingForm uploadChannelListingForm) throws ApiException {
        validateChannelListingForm(uploadChannelListingForm, productService, partyService, channelService );
        List<ChannelListingPojo> channelListingPojoList = convertToCLPojo(uploadChannelListingForm);
        service.add(channelListingPojoList);

    }

    public ChannelListingData getById(Long id) throws ApiException {
        return convert(service.getById(id), ChannelListingData.class);
    }

    public List<ChannelListingData> getAll(){
        List<ChannelListingPojo> pojoList = service.getAll();
        return pojoList.stream().map(x->convert(x,ChannelListingData.class)).collect(Collectors.toList());
    }

    private List<ChannelListingPojo> convertToCLPojo(UploadChannelListingForm uploadChannelListingForm) throws ApiException {
        List<ChannelListingPojo> channelListingPojoList = new ArrayList<>();
        for(ChannelListingForm form : uploadChannelListingForm.getFormList()){
            ChannelListingPojo cp = convert(form, ChannelListingPojo.class);
            cp.setClientId(validateClient(uploadChannelListingForm.getClientName(), partyService));
            cp.setChannelId(validateChannel(uploadChannelListingForm.getChannelName(), channelService));
            cp.setGlobalSkuId(getProduct(validateClient(uploadChannelListingForm.getClientName(),partyService),form.getClientSkuId()).getGlobalSkuId());
        }
        return channelListingPojoList;
    }

    private ProductPojo getProduct(Long clientId, String clientSkuId){
        return productService.getByClientSkuIdAndClientId(clientSkuId, clientId);
    }



}
