package com.increff.assure.dto;
import com.increff.assure.pojo.*;
import com.increff.assure.service.BinService;
import com.increff.assure.service.InventoryService;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.BinData;
import com.increff.commons.Data.BinSkuData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.BinSkuForm;
import com.increff.commons.Form.UpdateBinSkuForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.increff.commons.Util.ConvertUtil.convert;

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
        checkBinNumber(qty);
        return convert(service.add(getBinPojo(qty)),BinData.class);
    }

    public List<BinData> getAllBins() throws ApiException {
        List<BinPojo> listPojo =  service.getAllBins();
        return convert(listPojo, BinData.class);

    }
    @Transactional(rollbackOn = ApiException.class)
    public void addBinSku(Long clientId, List<BinSkuForm> binSkuFormList) throws ApiException {
        validateBinSkuClient(clientId, partyService);
        formValidation(clientId, binSkuFormList, service, productService);
        for(BinSkuForm binSkuForm:binSkuFormList) {
            BinSkuPojo binSkuPojo = convertBinSkuForm(clientId,binSkuForm);
            service.addBinSku(binSkuPojo);
            addInventory(binSkuPojo);
        }
    }

    public void updateBinSku(Long id, UpdateBinSkuForm binSkuForm) throws ApiException {
        validateUpdateQty(binSkuForm.getQty());
        BinSkuPojo binSkuPojo = service.getBinSku(id);
        updateInventory(binSkuPojo.getGlobalSkuId(), binSkuForm.getQty());
        service.updateQty(binSkuPojo, binSkuForm.getQty());
    }

    public BinSkuData getBinSku(Long id) throws ApiException {
        BinSkuPojo pojo = service.getBinSku(id);
        return convert(pojo, BinSkuData.class);
    }

    public List<BinSkuData> getAllBinSku(){
        List<BinSkuPojo> initialList = service.getAllBinSku();
        return initialList.stream().map(x->convert(x,BinSkuData.class)).collect(Collectors.toList());
    }


    private void getGSKUId(Long clientId, String clientSkuId, BinSkuPojo binSkuPojo){
        binSkuPojo.setGlobalSkuId(productService.getByClientSkuIdAndClientId(clientSkuId,clientId).getGlobalSkuId());
    }
    private List<BinPojo> getBinPojo(Long qty){
        BinPojo[] bpArray = new BinPojo[Math.toIntExact(qty)];
        return Arrays.asList(bpArray).stream().map(x->new BinPojo()).collect(Collectors.toList());
    }

    private void addInventory(BinSkuPojo binSkuPojo) throws ApiException {
        InventoryPojo ip = inventoryService.get(binSkuPojo.getGlobalSkuId());
        if(Objects.isNull(ip)){createInventory(binSkuPojo.getGlobalSkuId(), binSkuPojo.getQty());return;}
        inventoryService.updateAvailableQty(ip.getId(), binSkuPojo.getQty());
    }
    private void updateInventory(Long globalSkuId, Long qty) throws ApiException {
        InventoryPojo ip = inventoryService.getCheckGlobalSkuId(globalSkuId);
        inventoryService.updateAvailableQty(ip.getId(),qty);
    }

    private void createInventory(Long id, Long qty){
        InventoryPojo ip = new InventoryPojo();
        ip.setGlobalSkuId(id);
        ip.setAvailableQty(qty);
        inventoryService.add(ip);
    }
    private BinSkuPojo convertBinSkuForm(Long clientId, BinSkuForm binSkuForm){
        BinSkuPojo binSkuPojo = convert(binSkuForm, BinSkuPojo.class);
        getGSKUId(clientId,binSkuForm.getClientSkuId(),binSkuPojo);
        return binSkuPojo;
    }




}
