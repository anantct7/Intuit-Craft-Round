package com.example.intuit.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new ResponseEntity<>("Entity Not Found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> dataAccessException(Exception entityAlreadyFoundException) {
        return new ResponseEntity<>("Data Access Exception", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ProfileValidationException.class)
    public ResponseEntity<String> profileValidationException(ProfileValidationException profileValidationException) {
        return new ResponseEntity<>("Profile Validation Failed", HttpStatus.FORBIDDEN);
    }
}
