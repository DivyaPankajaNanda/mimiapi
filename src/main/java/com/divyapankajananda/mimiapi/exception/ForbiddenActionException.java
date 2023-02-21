package com.divyapankajananda.mimiapi.exception;

public class ForbiddenActionException extends Exception{
    
    public ForbiddenActionException(){
        
    }

    public ForbiddenActionException(String message){
        super(message);
    }
}
