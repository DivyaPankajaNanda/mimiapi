package com.divyapankajananda.mimiapi.exception;

public class ExpiredJwtTokenException extends Exception {
    
    public ExpiredJwtTokenException(){

    }

    public ExpiredJwtTokenException(String message){
        super(message);
    }
}
