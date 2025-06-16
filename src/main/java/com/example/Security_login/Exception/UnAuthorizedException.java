package com.example.Security_login.Exception;

public class UnAuthorizedException extends RuntimeException {

    public UnAuthorizedException(String message){
        super(message);
    }
}
