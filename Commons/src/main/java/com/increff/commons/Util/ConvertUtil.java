package com.increff.commons.Util;

import com.increff.commons.Exception.ApiException;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ConvertUtil {

    public static <T, D> T convert(D inObject, Class<T> OutObjectClass){
        T outObj = null;
        try{
            outObj = OutObjectClass.newInstance();
        }catch (InstantiationException | IllegalAccessException e){

        }

        List<Field> fieldListOutObject = getFields(outObj);

        HashMap<String, Field> nameFieldInObjMap = new HashMap<>();
        for(Field field:getFields(inObject)){
            nameFieldInObjMap.put(field.getName(), field);
        }

        for(Field fieldOut: fieldListOutObject){
            String fieldName = fieldOut.getName();
            if(nameFieldInObjMap.containsKey(fieldName)){
                fieldOut.setAccessible(true);
                Field fieldIn = nameFieldInObjMap.get(fieldName);
                fieldIn.setAccessible(true);
                ReflectionUtils.setField(fieldOut, outObj, ReflectionUtils.getField(nameFieldInObjMap.get(fieldName), inObject));
            }
        }
        return outObj;
    }

    public static <T, D> List<T> convert(List<D> inObjectList, Class<T> OutObjectClass) {
        List<T> returnList = new ArrayList<>();
        for(D inObj:inObjectList){
            T outObj = convert(inObj, OutObjectClass);
            returnList.add(outObj);
        }
        return returnList;
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
