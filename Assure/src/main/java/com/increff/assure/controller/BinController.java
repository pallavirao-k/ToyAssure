package com.increff.assure.controller;
import com.increff.assure.dto.BinDto;
import com.increff.commons.Data.BinData;
import com.increff.commons.Data.BinSkuData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.BinSkuSearchForm;
import com.increff.commons.Form.UpdateBinSkuForm;
import com.increff.commons.Form.UploadBinSkuForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api
@RestController
@RequestMapping("/api/bins")
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
    @RequestMapping(path = "/skus", method = RequestMethod.POST)
    public void uploadBinSkus(@RequestBody UploadBinSkuForm uploadBinSkuForm) throws ApiException {  //change name and mapping
        dto.uploadBinSku(uploadBinSkuForm);
    }


    @ApiOperation(value = "Updates a Bin wise inventory")
    @RequestMapping(path = "/skus", method = RequestMethod.PUT)
    public void updateBinSku(@RequestParam Long id, @RequestBody UpdateBinSkuForm binSkuForm) throws ApiException {
        dto.updateSingleBinSku(id, binSkuForm);
    }

    @ApiOperation(value = "Gets a BinSku by Id")
    @RequestMapping(path = "/skus/{id}", method=RequestMethod.GET)
    public BinSkuData getBinSku(@PathVariable Long id) throws ApiException {
        return dto.getBinSku(id);
    }

    @ApiOperation(value = "Gets a BinSku by Id")
    @RequestMapping(path = "/skus", method=RequestMethod.GET)
    public List<BinSkuData> getAllBinSku() throws ApiException {
        return dto.getAllBinSku();
    }

    @ApiOperation(value = "search bin sku by binId and globalSkuId")
    @RequestMapping(path = "/skus/search", method=RequestMethod.POST)
    public List<BinSkuData> search(@RequestBody BinSkuSearchForm form) throws ApiException {
        return dto.search(form);
    }





}
