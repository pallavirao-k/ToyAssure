package com.increff.assure.service;

import com.increff.assure.dao.InventoryDao;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.InventoryPojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    public void createInventory(Long id, Long qty){
        InventoryPojo ip = new InventoryPojo();
        ip.setGlobalSkuId(id);
        ip.setAvailableQty(qty);
        dao.insert(ip);
    }

    public void addInventory(BinSkuPojo binSkuPojo) throws ApiException {
        InventoryPojo ip = get(binSkuPojo.getGlobalSkuId());
        if(Objects.isNull(ip)){
            createInventory(binSkuPojo.getGlobalSkuId(), binSkuPojo.getQty());
        }
        updateAvailableQty(ip.getId(), binSkuPojo.getQty());
    }

    public void updateInventory(Long globalSkuId, Long qty) throws ApiException {
        InventoryPojo ip = getCheckGlobalSkuId(globalSkuId);
        updateAvailableQty(ip.getId(),qty);
    }

    public Long updateAvailableAndAllocatedQty(Long orderedQty, Long globalSkuId) throws ApiException {
        InventoryPojo inventoryPojo = getCheckGlobalSkuId(globalSkuId);
        Long qtyToAllocate = Math.min(orderedQty, inventoryPojo.getAvailableQty());
        inventoryPojo.setAvailableQty(inventoryPojo.getAvailableQty()-qtyToAllocate);
        inventoryPojo.setAllocatedQty(inventoryPojo.getAllocatedQty()+qtyToAllocate);
        return qtyToAllocate;
    }



    public InventoryPojo get(Long globalSkuId){
        return dao.selectByGlobalSkuId(globalSkuId);
    }

    public void updateAvailableQty(Long id, Long qty) throws ApiException {
        InventoryPojo ip = dao.select(id);
        if(ip.getAllocatedQty()>qty){
            throw new ApiException("Available quantity cannot be less than Allocated quantity");
        }
        ip.setAvailableQty(qty);

    }

    public InventoryPojo getCheckGlobalSkuId(Long id) throws ApiException {
        InventoryPojo ip = dao.selectByGlobalSkuId(id);
        if(Objects.isNull(ip))throw  new ApiException("Inventory with Global SKU ID: "+id+" doesn't exist");
        return ip;
    }

}
