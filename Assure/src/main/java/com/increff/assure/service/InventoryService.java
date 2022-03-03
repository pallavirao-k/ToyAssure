package com.increff.assure.service;

import com.increff.assure.dao.InventoryDao;
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

    public void add(InventoryPojo ip){
        dao.insert(ip);
    }

    public InventoryPojo get(Long globalSkuId){
        return dao.selectByGlobalSkuId(globalSkuId);
    }

    public void updateAvailableQty(Long id, Long qty) throws ApiException {
        InventoryPojo ip = dao.select(id);
        if(ip.getAllocatedQty()<=qty){ip.setAvailableQty(qty);return;}
        throw new ApiException("Available quantity cannot be less than Allocated quantity");
    }

    public InventoryPojo getCheckGlobalSkuId(Long id) throws ApiException {
        InventoryPojo ip = dao.selectByGlobalSkuId(id);
        if(Objects.isNull(ip))throw  new ApiException("Inventory with Global SKU ID: "+id+" doesn't exist");
        return ip;
    }
}
