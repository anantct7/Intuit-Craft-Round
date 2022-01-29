package com.example.intuit.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {

    private String message;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
