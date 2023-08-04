package com.divyapankajananda.mimiapi.exception;

public class ForbiddenActionException extends RuntimeException{
    public ForbiddenActionException(String message){
        super(message);
    }    
}
