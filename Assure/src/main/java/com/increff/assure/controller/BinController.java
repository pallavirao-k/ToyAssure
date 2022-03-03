package com.increff.assure.controller;
import com.increff.assure.dto.BinDto;
import com.increff.commons.Data.BinData;
import com.increff.commons.Data.BinSkuData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.BinSkuForm;
import com.increff.commons.Form.UpdateBinSkuForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/bin")
public class BinController {

    @Autowired
    private BinDto dto;


    @ApiOperation(value = "Adds bins")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public List<BinData> add(@RequestParam Long qty) throws ApiException {
        return dto.add(qty);
    }

    @ApiOperation(value = "Gets all bins")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<BinData> getAllBins() throws ApiException {
        return dto.getAllBins();
    }

    @ApiOperation(value = "Adds Bin wise inventory against clientId")
    @RequestMapping(path = "/sku/add/", method = RequestMethod.POST)
    public void addBinSku(@RequestParam Long clientId, @RequestBody List<BinSkuForm> binSkuList) throws ApiException {
        dto.addBinSku(clientId, binSkuList);
    }

    @ApiOperation(value = "Updates a Bin wise inventory")
    @RequestMapping(path = "/sku/update/{id}", method = RequestMethod.POST)
    public void UpdateBinSku(@PathVariable Long id, @RequestBody UpdateBinSkuForm binSkuForm) throws ApiException {
        dto.updateBinSku(id, binSkuForm);
    }


    @ApiOperation(value = "Gets all BinSku")
    @RequestMapping(path = "/sku", method=RequestMethod.GET)
    public List<BinSkuData> getAllBinSku() throws ApiException {
        return dto.getAllBinSku();
    }

    @ApiOperation(value = "Gets a BinSku by Id")
    @RequestMapping(path = "/sku/{id}", method=RequestMethod.GET)
    public BinSkuData getBinSku(@PathVariable Long id) throws ApiException {
        return dto.getBinSku(id);
    }





}
