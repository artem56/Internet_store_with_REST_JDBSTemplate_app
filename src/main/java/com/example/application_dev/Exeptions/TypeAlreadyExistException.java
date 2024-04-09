package com.example.application_dev.Exeptions;

public class TypeAlreadyExistException extends Exception {
    public TypeAlreadyExistException(String message) {
        super(message);
    }
}