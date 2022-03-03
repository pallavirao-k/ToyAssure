package com.increff.assure.dto;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.ProductData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.ProductForm;
import com.increff.commons.Form.UpdateProductForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void add(Long clientId, List<ProductForm> formList) throws ApiException{
        formValidationProduct(formList);
        List<ProductPojo> productPojoList = convert(formList, ProductPojo.class);
        validateProductClient(clientId, productPojoList, partyService);
        normalize(productPojoList);
        service.add(clientId, productPojoList);
    }

    public void update(Long id, UpdateProductForm form) throws ApiException{
        validateUpdateProductForm(form);
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








}
