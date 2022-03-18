package com.increff.commons.Util;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    private static RestTemplate restTemplate = new RestTemplate();

    public static <T> ResponseEntity<String> postRequest(String url, T requestBody){
        return restTemplate.postForEntity(url, requestBody, String.class);
    }


}
