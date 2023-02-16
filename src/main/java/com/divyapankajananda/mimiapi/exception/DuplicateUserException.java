package com.divyapankajananda.mimiapi.exception;

public class DuplicateUserException extends RuntimeException{
    public DuplicateUserException(){

    }

    public DuplicateUserException(String message){
        super(message);
    }
}
