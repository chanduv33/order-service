package com.storesmanagementsystem.order.exceptions;

import com.storesmanagementsystem.order.contracts.CommonResponse;
import com.storesmanagementsystem.order.contracts.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class HandlerForExceptions {


//    @ExceptionHandler(IllegalArgumentException.class)
//    ResponseEntity<CommonResponse> handleIllegalArgumentExceptions(IllegalArgumentException ex){
//        if(null != ex.getCause().getMessage()){
//            return  getErrorDetails(ex.getCause().getMessage());
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getDefaultErrorDetails(ex.getCause().getMessage()));
//    }

    @ExceptionHandler(DetailsNotFoundException.class)
    ResponseEntity<CommonResponse> handleDNFExceptions(DetailsNotFoundException ex){
        if(null != ex.getMessage()){
            return  getErrorDetails(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getDefaultErrorDetails(ex.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ResponseEntity<CommonResponse> handleMissingParamsExcep(MissingServletRequestParameterException ex){
        if(null != ex.getMessage()){
            return  getErrorDetails(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getDefaultErrorDetails(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<CommonResponse> handleGeneralExcep(Exception ex){
        if(ex.getCause() instanceof IllegalArgumentException) {
            return  getErrorDetails(ex.getCause().getMessage());
        }
        if(null != ex.getMessage()){
            return  getErrorDetails(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getDefaultErrorDetails(ex.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<CommonResponse> handleAuthentication(AuthenticationException ex){
        if(null != ex.getMessage()){
            return  getErrorDetails(ex.getMessage());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getDefaultErrorDetails(ex.getMessage()));
    }



    private ResponseEntity<CommonResponse> getErrorDetails(String errorMsg){
        Error error = new Error("IN400",errorMsg);
        CommonResponse CommonResponse = new CommonResponse();
        CommonResponse.setErrors(Arrays.asList(error));
        return ResponseEntity.badRequest().body(CommonResponse);
    }

    private CommonResponse getDefaultErrorDetails(String errorMsg){
        Error error = new Error("IN500",errorMsg);
        CommonResponse CommonResponse = new CommonResponse();
        CommonResponse.setErrors(Arrays.asList(error));
        return CommonResponse;
    }
}
