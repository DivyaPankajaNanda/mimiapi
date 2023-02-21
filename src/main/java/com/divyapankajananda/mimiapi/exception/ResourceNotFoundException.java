package com.divyapankajananda.mimiapi.exception;

public class ResourceNotFoundException extends Exception{
    
    public ResourceNotFoundException(){

    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
