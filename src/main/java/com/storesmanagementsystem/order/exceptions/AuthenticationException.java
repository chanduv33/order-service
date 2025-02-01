package com.storesmanagementsystem.order.exceptions;

public class AuthenticationException extends RuntimeException{

    String message;
    public AuthenticationException(){

    }

    public AuthenticationException(String msg){
        this.message = msg;
    }
}
