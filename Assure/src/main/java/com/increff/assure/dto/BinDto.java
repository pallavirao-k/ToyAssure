package com.increff.assure.dto;
import com.increff.assure.pojo.*;
import com.increff.assure.service.BinService;
import com.increff.assure.service.InventoryService;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.BinData;
import com.increff.commons.Data.BinSkuData;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.BinSkuForm;
import com.increff.commons.Form.UpdateBinSkuForm;
import com.increff.commons.Form.UploadBinSkuForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.increff.commons.Util.ConvertUtil.convert;
import static com.increff.commons.Util.ValidationUtil.checkLimit;

@Service
public class BinDto extends AbstractDto {

    @Autowired
    private BinService service;
    @Autowired
    private PartyService partyService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    public List<BinData> add(Long qty) throws ApiException {
        checkLimit(qty, "Bins count");
        return convert(service.add(getBinPojo(qty)),BinData.class);
    }

    public List<BinData> getAllBins(){
        List<BinPojo> listPojo =  service.getAllBins();
        return convert(listPojo, BinData.class);

    }
    @Transactional(rollbackOn = ApiException.class)
    public void uploadBinSku(UploadBinSkuForm uploadBinSkuForm) throws ApiException {
        validateClient(uploadBinSkuForm.getClientId());
        formValidation(uploadBinSkuForm);
        checkBinIds(uploadBinSkuForm.getFormList());
        validateClientSkuId(uploadBinSkuForm.getFormList());
        checkClientSkuId(uploadBinSkuForm.getClientId(), uploadBinSkuForm.getFormList());
        for(BinSkuForm binSkuForm:uploadBinSkuForm.getFormList()) {
            BinSkuPojo binSkuPojo = convertBinSkuForm(uploadBinSkuForm.getClientId(),binSkuForm);
            service.addBinSku(binSkuPojo);
            inventoryService.addInventory(binSkuPojo);
        }
    }

    public void updateSingleBinSku(Long id, UpdateBinSkuForm binSkuForm) throws ApiException {
        BinSkuPojo binSkuPojo = service.getBinSku(id);
        inventoryService.updateInventory(binSkuPojo.getGlobalSkuId(), binSkuForm.getQty());
        service.updateQty(binSkuPojo, binSkuForm.getQty());
    }

    public BinSkuData getBinSku(Long id) throws ApiException {
        BinSkuPojo pojo = service.getBinSku(id);
        return convert(pojo, BinSkuData.class);
    }

    private void getGSKUId(Long clientId, String clientSkuId, BinSkuPojo binSkuPojo){
        binSkuPojo.setGlobalSkuId(productService.getByClientSkuIdAndClientId(clientSkuId,clientId).getGlobalSkuId());
    }
    private List<BinPojo> getBinPojo(Long qty){
        BinPojo[] bpArray = new BinPojo[Math.toIntExact(qty)];
        return Arrays.asList(bpArray).stream().map(x->new BinPojo()).collect(Collectors.toList());
    }


    private BinSkuPojo convertBinSkuForm(Long clientId, BinSkuForm binSkuForm){
        BinSkuPojo binSkuPojo = convert(binSkuForm, BinSkuPojo.class);
        getGSKUId(clientId,binSkuForm.getClientSkuId(),binSkuPojo);
        return binSkuPojo;
    }

    private void checkBinIds(List<BinSkuForm> formList) throws ApiException {
        List<Long> initialBinIds = formList.stream().map(BinSkuForm::getBinId).collect(Collectors.toList());
        List<BinPojo> finalBinPojos = service.getCheckBinIdList(initialBinIds);
        List<Long> finalBinIds = finalBinPojos.stream().map(BinPojo::getBinId).collect(Collectors.toList());
        List<Long> differences = initialBinIds.stream().filter(element -> !finalBinIds.contains(element)).collect(Collectors.toList());

        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("Bin with these ID(s) doesn't exist.",differences));
        }
    }

    private void validateClientSkuId(List<BinSkuForm> formList ) throws ApiException {
        Long index=1L;
        List<Long> indexes = new ArrayList<>();
        Set<String> clientSkuIds = new HashSet<>();
        List<Long> indexes2 = new ArrayList<>();
        for(BinSkuForm form: formList){
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
            throw new ApiException(ErrorData.convert("Client SKU ID(s) of these indexes are blank", indexes));
        }
        if(indexes2.size()>0){
            throw new ApiException(ErrorData.convert("Client SKU ID(s) at these indexes are being repeated", indexes2));
        }

    }




    private void checkClientSkuId(Long clientId, List<BinSkuForm> formList) throws ApiException {

        List<String> initialClientSkuIds = formList.stream().map(BinSkuForm::getClientSkuId).collect(Collectors.toList());
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, initialClientSkuIds);
        List<String> finalClientSkuIds = pojoList.stream().map(ProductPojo::getClientSkuId).collect(Collectors.toList());
        List<String> differences = initialClientSkuIds.stream().filter(element -> !finalClientSkuIds.contains(element)).collect(Collectors.toList());

        if(differences.size()>0){
            throw new ApiException(ErrorData.convert("These Client SKU ID(s) don't exist", differences));
        }

    }

    private void validateClient(Long clientId) throws ApiException {
        partyService.getCheck(clientId);
    }






}
