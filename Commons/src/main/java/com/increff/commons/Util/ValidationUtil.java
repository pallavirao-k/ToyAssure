package com.increff.commons.Util;

import com.increff.commons.Exception.ApiException;

import static com.increff.commons.Constants.ConstantNames.SEQ_MAX_VAL;
import static com.increff.commons.Constants.ConstantNames.SEQ_MIN_VAL;

public class ValidationUtil {

    public static void checkLimit(Long qty) throws ApiException {
        if(qty<=SEQ_MIN_VAL){
            throw new ApiException("Quantity should be greater than zero");
        }
        if(qty>SEQ_MAX_VAL){
            throw new ApiException("Quantity exceeds limit "+SEQ_MAX_VAL);
        }
    }
}
