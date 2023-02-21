package com.divyapankajananda.mimiapi.advice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.divyapankajananda.mimiapi.dto.CustomExceptionDto;
import com.divyapankajananda.mimiapi.exception.ForbiddenActionException;
import com.divyapankajananda.mimiapi.exception.ResourceNotFoundException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {


        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        CustomExceptionDto exception = CustomExceptionDto.builder()
                .message(ex.getLocalizedMessage())
                .errors(errors)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(exception);

    }

    @ExceptionHandler({ ForbiddenActionException.class })
    protected ResponseEntity<Object> handleForbiddenException(ForbiddenActionException ex, WebRequest request) {
        CustomExceptionDto exception = CustomExceptionDto.builder()
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(exception);

    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        CustomExceptionDto exception = CustomExceptionDto.builder()
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(exception);

    }


    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        CustomExceptionDto exception = CustomExceptionDto.builder()
                .message(ex.getLocalizedMessage())
                .errors(List.of("error occured"))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception);
    }

}
