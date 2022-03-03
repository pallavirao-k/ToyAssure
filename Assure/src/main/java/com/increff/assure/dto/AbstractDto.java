package com.increff.assure.dto;

import com.increff.assure.pojo.BinPojo;
import com.increff.assure.pojo.ProductPojo;
import com.increff.assure.service.BinService;
import com.increff.assure.service.ChannelService;
import com.increff.assure.service.PartyService;
import com.increff.assure.service.ProductService;
import com.increff.commons.Data.ErrorData;
import com.increff.commons.Exception.ApiException;
import com.increff.commons.Form.*;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.*;

@Service
public class AbstractDto {


    public static <T> void constraintValidation(T form) throws ApiException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> errors = validator.validate(form);
        Iterator<ConstraintViolation<T>> itErrors = errors.iterator();
        if(itErrors.hasNext()) {
            ConstraintViolation<T> violation = itErrors.next();
            throw new ApiException("Constraint Violation " + violation.getPropertyPath() + " " + violation.getMessage());
        }
    }

    public static <T> void constraintValidationList(List<T> formList) throws ApiException {
        for(T form : formList ){
            constraintValidation(form);
        }
    }

    /*-------------- Party Dto  starts --------------------*/

    public void validatePartyForm(PartyForm form) throws ApiException {
        constraintValidation(form);
        if(form.getPartyName().trim().isEmpty()){
            throw new ApiException("Party Name must not be empty");
        }
    }

    /*-------------- Party Dto  ends --------------------*/



    /*-------------- Product Dto  starts --------------------*/

    public void formValidationProduct(List<ProductForm> formList) throws ApiException {
        constraintValidationList(formList);
        List<List<ErrorData>> validatingList = new ArrayList<>();
        validatingList.add(validateProductName(formList));
        validatingList.add(validateProductBrandId(formList));
        validatingList.add(validateProductMRP(formList));
        validatingList.add(validateProductDesc(formList));

        for(List<ErrorData> l:validatingList){
            if(l.size()>0){
                throw new ApiException(ErrorData.convert(l));
            }
        }
    }

    public void validateUpdateProductForm(UpdateProductForm form) throws ApiException {
        constraintValidation(form);
        if(form.getBrandId().trim().isEmpty()){
            throw new ApiException("Brand ID must not be empty");
        }
        else if(form.getProductName().trim().isEmpty()){
            throw new ApiException("Product name must not be empty");
        }
        else if(form.getProductMrp()<=0){
            throw new ApiException("Product mrp must be greater than zero");
        }
        else if(form.getDescription().trim().isEmpty()){
            throw new ApiException("Product description must not be empty");
        }
    }

    public void validateProductClient(Long id, List<ProductPojo> pojoList, PartyService partyService) throws ApiException {
        partyService.getCheck(id);
        for(ProductPojo p: pojoList){
            p.setClientId(id);
        }

    }
    public void validateBinSkuClient(Long clientId, PartyService partyService) throws ApiException {
        partyService.getCheck(clientId);
    }

    private List<ErrorData> validateProductName(List<ProductForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(ProductForm form: formList){
            if(form.getProductName().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Name must not be empty");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;
    }

    private List<ErrorData> validateProductBrandId(List<ProductForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(ProductForm form: formList){
            if(form.getBrandId().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Brand ID must not be empty");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;
    }

    private List<ErrorData> validateProductMRP(List<ProductForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(ProductForm form: formList){
            if(form.getProductMrp()<=0){
                ErrorData errorData = new ErrorData(sno, "MRP must be greater than zero");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;
    }

    private List<ErrorData> validateProductDesc(List<ProductForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(ProductForm form: formList){
            if(form.getDescription().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Description must not be empty");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;
    }


    /*------------- Product Dto Ends ---------------------*/




    /*------------- Product Dto Starts ---------------------*/


    public void formValidation(Long clientId, List<BinSkuForm> formList, BinService service, ProductService productService) throws ApiException {
        constraintValidationList(formList);
        List<List<ErrorData>> validatingList = new ArrayList<>();
        validatingList.add(validateBinId(formList, service));
        validatingList.add(validateClientSkuId(clientId,formList, productService));
        validatingList.add(validateQty(formList));

        for(List<ErrorData> l:validatingList){
            if(l.size()>0){
                throw new ApiException(ErrorData.convert(l));
            }
        }
    }

    public void checkBinNumber(Long qty) throws ApiException {
        if(qty<=0){
            throw new ApiException("Quantity should be greater than zero");
        }
        if(qty>5000){
            throw new ApiException("Quantity exceeds limit");
        }
    }

    public void validateUpdateQty(Long qty) throws ApiException {
        if(qty<0)throw new ApiException("Quantity must not be negative");
    }

    private List<ErrorData> validateBinId(List<BinSkuForm> formList, BinService service) throws ApiException {
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(BinSkuForm form: formList){
            BinPojo b = service.protectedGet(form.getBinId());
            if(Objects.isNull(b)){
                ErrorData errorData = new ErrorData(sno, "Bin with ID: "+form.getBinId()+" doesn't exist");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;
    }

    private List<ErrorData> validateClientSkuId(Long clientId, List<BinSkuForm> formList, ProductService productService) throws ApiException {
        List<ErrorData> errorDataList = new ArrayList<>();
        List<String> clientSkuIds = new ArrayList<>();
        Long sno=1L;
        for(BinSkuForm form: formList){
            if(form.getClientSkuId().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Client SKU ID must not be empty");
                errorDataList.add(errorData);
            }
            else{
                clientSkuIds.add(form.getClientSkuId());
            }
            sno++;
        }
        if(errorDataList.size()!=0)return errorDataList;
        sno=1L;
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, clientSkuIds);

            if(pojoList.size()<formList.size()){
                ErrorData errorData = new ErrorData(sno, "Enter valid Client SKU ID");
                errorDataList.add(errorData);
            }

        return errorDataList;
    }

    private List<ErrorData> validateQty(List<BinSkuForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(BinSkuForm form: formList){
            if(form.getQty()<=0){
                ErrorData errorData = new ErrorData(sno, "Quantity must be greater than zero");
                errorDataList.add(errorData);
                sno++;
            }

        }
        return errorDataList;

    }

    //private


    /*---------------------- BinDto ends ------------------------------*/




    /*---------------------- ChannelDto starts ------------------------------*/

    public void validateChannelForm(ChannelForm form) throws ApiException {
        constraintValidation(form);
        if(form.getChannelName().trim().isEmpty()){
            throw new ApiException("Channel name must not be empty");
        }
    }

    /*---------------------- ChannelDto ends ------------------------------*/


    /*---------------------- ChannelListingDto starts ------------------------------*/


    public Long validateClient(String clientName, PartyService partyService) throws ApiException {
        return partyService.getCheckClientByName(clientName).getPartyId();
    }

    public Long validateChannel(String channelName, ChannelService channelService) throws ApiException {
        return channelService.getCheckByName(channelName).getId();
    }

    public void validateChannelListingForm(UploadChannelListingForm uploadChannelListingForm, ProductService productService, PartyService partyService, ChannelService channelService) throws ApiException {
        constraintValidation(uploadChannelListingForm);
        constraintValidationList(uploadChannelListingForm.getFormList());
        Long clientId = validateClient(uploadChannelListingForm.getClientName(), partyService);
        validateChannel(uploadChannelListingForm.getChannelName(), channelService);
        List<List<ErrorData>> validatingList = new ArrayList<>();
        validatingList.add(validateChannelSkuId(uploadChannelListingForm.getFormList()));
        validatingList.add(validateClientSkuIdCL(clientId, uploadChannelListingForm.getFormList(), productService));


        for(List<ErrorData> l:validatingList){
            if(l.size()>0){
                throw new ApiException(ErrorData.convert(l));
            }
        }
    }

    public List<ErrorData> validateClientSkuIdCL(Long clientId, List<ChannelListingForm> formList, ProductService productService) throws ApiException {
        List<ErrorData> errorDataList = new ArrayList<>();
        List<String> clientSkuIds = new ArrayList<>();
        Long sno=1L;
        boolean flag = true;
        for(ChannelListingForm form: formList){
            clientSkuIds.add(form.getChannelSkuId());
            if(form.getClientSkuId().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Client SKU ID must not be empty");
                errorDataList.add(errorData);
                flag=false;
            }
            sno++;
        }
        if(errorDataList.size()!=0)return errorDataList;
        List<ProductPojo> pojoList = productService.getByClientSkuIdAndClientIds(clientId, clientSkuIds);

        if(pojoList.size()<formList.size()){
            ErrorData errorData = new ErrorData(sno, "Enter valid Client SKU ID");
            errorDataList.add(errorData);
        }

        return errorDataList;
    }

    public List<ErrorData> validateChannelSkuId(List<ChannelListingForm> formList){
        List<ErrorData> errorDataList = new ArrayList<>();
        Long sno=1L;
        for(ChannelListingForm form: formList){
            if(form.getChannelSkuId().trim().isEmpty()){
                ErrorData errorData = new ErrorData(sno, "Channel SKU ID must not be empty");
                errorDataList.add(errorData);
                sno++;
            }
        }
        return errorDataList;

    }


}
