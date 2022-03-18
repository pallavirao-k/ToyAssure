package com.increff.channelapp.controller;

import com.increff.commons.Data.MessageData;
import com.increff.commons.Exception.ApiException;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class RestControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ApiException.class})
    @ResponseBody
    protected MessageData handleApiException(HttpServletRequest req, ApiException e){
        e.setStackTrace(e.getStackTrace());
        MessageData data = new MessageData();
        data.setMessage(e.getMessage());
        return data;
    }


    @ExceptionHandler(value = {HttpServerErrorException.class})
    protected ResponseEntity<Object> handleConflict(HttpServerErrorException ex, WebRequest request){

        return handleExceptionInternal(ex, ex.getResponseBodyAsString(),
                new HttpHeaders(), ex.getStatusCode(), request);
    }

}
