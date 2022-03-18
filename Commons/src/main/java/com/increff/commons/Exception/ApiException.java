package com.increff.commons.Exception;

import java.util.List;
import java.util.Map;

public class ApiException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    int code;
    Map<String, List<String>> responseHeaders;
    String responseBody;
    String requestURI;
    String requestBody;




    public ApiException(String s) {
        super(s);
    }

    public ApiException(String message, Throwable throwable, int code, Map<String, List<String>> responseHeaders,
                        String responseBody, String requestURI, String requestBody) {
        super(message, throwable);
        this.code = code;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
        this.requestURI = requestURI;
        this.requestBody = requestBody;
    }

}
