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


    public static <T> void formValidation(T form) throws ApiException {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> errors = validator.validate(form);
        Iterator<ConstraintViolation<T>> itErrors = errors.iterator();
        if(itErrors.hasNext()) {
            ConstraintViolation<T> violation = itErrors.next();
            throw new ApiException("Constraint Violation " + violation.getPropertyPath() + " " + violation.getMessage());
        }
    }

    public static <T> void formListValidation(List<T> formList) throws ApiException {
        for(T form : formList ){
            formValidation(form);
        }
    }








}
