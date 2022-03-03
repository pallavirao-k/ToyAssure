//package com.increff.assure.controller;
//
//import com.increff.commons.Data.MessageData;
//import com.increff.commons.Exception.ApiException;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//import javax.servlet.http.HttpServletRequest;
//
//
//@ControllerAdvice
//public class RestAppControllerAdvice extends ResponseEntityExceptionHandler {
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler({ApiException.class})
//    @ResponseBody
//    protected MessageData handleApiException(HttpServletRequest req, ApiException e){
//        e.setStackTrace(e.getStackTrace());
//        MessageData data = new MessageData();
//        data.setMessage(e.getMessage());
//        return data;
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler({Throwable.class})
//    @ResponseBody
//    protected MessageData handleUnknownException(HttpServletRequest req, Throwable t){
//        t.setStackTrace(t.getStackTrace());
//        MessageData data = new MessageData();
//        data.setMessage("Internal error of assure");
//        return data;
//    }
//
//
//}
