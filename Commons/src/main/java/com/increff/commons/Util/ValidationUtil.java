package com.increff.commons.Util;

import com.increff.commons.Data.ErrorData;
import com.increff.commons.Exception.ApiException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.increff.commons.Constants.ConstantNames.SEQ_MAX_VAL;
import static com.increff.commons.Constants.ConstantNames.SEQ_MIN_VAL;

public class ValidationUtil {

    public static void checkLimit(Long qty, String entity) throws ApiException {
        if(qty<=SEQ_MIN_VAL){
            throw new ApiException(entity+" should be greater than zero");
        }
        if(qty>SEQ_MAX_VAL){
            throw new ApiException(entity+" exceeds limit "+SEQ_MAX_VAL);
        }
    }

    public static void validateEmptyField(String fieldVal, String entity) throws ApiException {
        if(fieldVal.trim().isEmpty()){
            throw new ApiException(entity+" must not be empty");
        }
    }

    public static void validateEmptyFields(List<String> inList, String entity) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Long index = 1L;
        for(String obj:inList){
            if(obj.trim().isEmpty()){
                indexes.add(index);
            }
            index++;
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert(entity+" are empty at indexes: ", indexes));
        }
    }


    public static <T>void checkDuplicates(List<T> inList, String entity) throws ApiException {
        List<Long> indexes = new ArrayList<>();
        Set<T> entities = new HashSet<>();
        Long index=1L;
        for(T obj:inList){
            if(!entities.contains(obj)){
                entities.add(obj);
            }
            else{
                indexes.add(index);
            }
            index++;
        }
        if(indexes.size()>0){
            throw new ApiException(ErrorData.convert(entity+ " are being repeated at indexes: ", indexes));
        }
    }
}
