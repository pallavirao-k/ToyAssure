package com.increff.assure.util;

import com.increff.commons.Exception.ApiException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizeUtil {

    public static <T> void normalize(T targetObject) throws ApiException {
        List<Field> fieldList = getFields(targetObject);

        for(Field field : fieldList){
            field.setAccessible(true);
            if(field.getType().equals(java.lang.String.class)){
                try {
                    String value = (String) field.get(targetObject);
                    if (value != null) {
                        field.set(targetObject, value.toLowerCase().trim());
                    }
                }catch(Exception e){
                    throw new ApiException("Bad normalization");
                }
            }
        }
    }

    public static <T> void normalize(List<T> tragetObjectList) throws ApiException {
        for(T obj:tragetObjectList){
            normalize(obj);
        }
    }

    private static <T>List<Field> getFields(T targetObject){
        List<Field>fieldsToReturn = new ArrayList<>();
        Class clazz = targetObject.getClass();
        while(clazz != Object.class){
            fieldsToReturn.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldsToReturn;
    }
}
