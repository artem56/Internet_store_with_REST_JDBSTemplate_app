package com.example.application_dev.Exeptions;

public class TypeNotFoundException extends RuntimeException {
    public TypeNotFoundException(String message) {
        super(message);
    }
}
