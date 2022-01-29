package com.example.intuit.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProfileValidationException extends RuntimeException {

    private String message;

    public ProfileValidationException(String message) {
        super(message);
    }
}
