package com.increff.assure.service;

import com.increff.assure.dao.InventoryDao;
import com.increff.assure.pojo.BinSkuPojo;
import com.increff.assure.pojo.InventoryPojo;
import com.increff.assure.pojo.OrderItemPojo;
import com.increff.commons.Exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(rollbackOn = ApiException.class)
public class InventoryService {

    @Autowired
    private InventoryDao dao;

    public void createInventory(Long globalSkuId, Long qty){
        InventoryPojo ip = new InventoryPojo();
        ip.setGlobalSkuId(globalSkuId);
        ip.setAvailableQty(qty);
        dao.insert(ip);
    }

    public void addInventory(BinSkuPojo binSkuPojo) throws ApiException {
        InventoryPojo ip = getInventory(binSkuPojo.getGlobalSkuId());
        //System.out.println(Objects.isNull(ip));
        if(Objects.isNull(ip)){
            createInventory(binSkuPojo.getGlobalSkuId(), binSkuPojo.getQty());
            return;
        }
        updateAvailableQty(ip.getId(), binSkuPojo.getQty());
    }

    public void updateInventory(Long globalSkuId, Long qty) throws ApiException {
        InventoryPojo ip = getCheckGlobalSkuId(globalSkuId);
        updateAvailableQty(ip.getId(),qty);
    }

    public Long updateAvailableAndAllocatedQty(Long orderedQty, Long globalSkuId) throws ApiException {
        InventoryPojo inventoryPojo = getCheckGlobalSkuId(globalSkuId);
        // add minus logic here
        // add checks
        Long qtyToAllocate = Math.min(orderedQty, inventoryPojo.getAvailableQty());
        inventoryPojo.setAvailableQty(inventoryPojo.getAvailableQty()-qtyToAllocate);
        inventoryPojo.setAllocatedQty(inventoryPojo.getAllocatedQty()+qtyToAllocate);
        return qtyToAllocate;
    }

    public void updateFulfilledQty(List<OrderItemPojo> list) throws ApiException {
        for(OrderItemPojo pojo: list){
            InventoryPojo inventoryPojo = getCheckGlobalSkuId(pojo.getGlobalSkuId());// avoid single checks
            inventoryPojo.setAllocatedQty(inventoryPojo.getAllocatedQty()-pojo.getOrderedQty());
            inventoryPojo.setFulfilledQty(inventoryPojo.getFulfilledQty()+pojo.getOrderedQty());
        }
    }


    public InventoryPojo getInventory(Long globalSkuId){
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
