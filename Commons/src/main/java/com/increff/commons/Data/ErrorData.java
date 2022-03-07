package com.increff.commons.Data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorData {

    public static<T> String convert(String message, List<T> errorList){
        try{
            return message+" "+JSONUtil.serialize(errorList);
        }catch (JSONException e){
            return "unable to convert error list to json";
        }
    }


}
