package com.increff.assure.dto;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Data.ProductData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ProductForm;
import com.increff.commons.Form.UpdateProductForm;
import com.increff.commons.Form.UploadProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.increff.assure.util.NormalizeUtil.normalize;
import static com.increff.commons.Util.ConvertUtil.convert;

@Service
public class ProductDto extends AbstractDto {

    @Autowired
    private ProductService service;
    @Autowired
    private PartyService partyService;

    public void add(UploadProductForm uploadProductForm) throws ApiException{
        formValidation(uploadProductForm);
        validateProductName(uploadProductForm.getFormList());
        validateBrandId(uploadProductForm.getFormList());
        validateProductDesc(uploadProductForm.getFormList());
        List<ProductPojo> productPojoList = convert(uploadProductForm.getFormList(), ProductPojo.class);
        checkClient(uploadProductForm.getClientId(), productPojoList);
        normalize(productPojoList);
        service.add(uploadProductForm.getClientId(), productPojoList);
    }

    public void update(Long id, UpdateProductForm form) throws ApiException{
        formValidation(form);
        checkForm(form);
        ProductPojo p = convert(form, ProductPojo.class);
        normalize(p);
        service.update(id, p);
    }

    public ProductData get(Long id) throws ApiException {
        ProductPojo p = service.get(id);
        return convert(p, ProductData.class);

    }
    public List<ProductData> getAll(){
        List<ProductPojo> list = service.getAll();
        return list.stream().map(x->convert(x,ProductData.class)).collect(Collectors.toList());
    }

    public void checkClient(Long id, List<ProductPojo> pojoList) throws ApiException {
        partyService.getCheck(id);
        for(ProductPojo p: pojoList){
            p.setClientId(id);
        }

    }

    public void checkForm(UpdateProductForm form) throws ApiException {

        if(form.getBrandId().trim().isEmpty()){
            throw new ApiException("Brand ID must not be empty");
        }
        else if(form.getProductName().trim().isEmpty()){
            throw new ApiException("Product name must not be empty");
        }
        else if(form.getDescription().trim().isEmpty()){
            throw new ApiException("Product description must not be empty");
        }
    }

    private void validateProductName(List<ProductForm> formList) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Long index=1L;
        for(ProductForm form: formList){
            if(form.getProductName().trim().isEmpty()){
                indexes.add(index);
                index++;
            }
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Product Name(s) of these rows are blank", indexes));
        }

    }

    private void validateBrandId(List<ProductForm> formList) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Long index=1L;
        for(ProductForm form: formList){
            if(form.getBrandId().trim().isEmpty()){
                indexes.add(index);
                index++;
            }
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Brand ID(s) of these rows are blank", indexes));
        }
    }


    private void validateProductDesc(List<ProductForm> formList) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Long index=1L;
        for(ProductForm form: formList){
            if(form.getDescription().trim().isEmpty()){
                indexes.add(index);
                index++;
            }
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert("Product Description(s) of these rows are blank", indexes));
        }
    }








}
