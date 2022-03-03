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

    private Long idx;
    private String message;

    public static String convert(List<ErrorData> errorDataList){
        try{
            return JSONUtil.serialize(errorDataList);
        }catch (JSONException e){
            return "unable to convert error list to json";
        }
    }


}
