package com.increff.assure.controller;
import com.increff.assure.dto.ProductDto;
import com.increff.commons.Data.ProductData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ProductForm;
import com.increff.commons.Form.UpdateProductForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api
@RestController()
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Adds a list of  products")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestParam Long clientId, @RequestBody List<ProductForm> formList) throws ApiException{
        dto.add(clientId, formList);
    }

    @ApiOperation(value = "Updates a product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable Long id, @RequestBody UpdateProductForm form) throws ApiException{
        dto.update(id, form);

    }

    @ApiOperation(value = "Gets a single product by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable Long id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all products")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
       return dto.getAll();
    }
}
