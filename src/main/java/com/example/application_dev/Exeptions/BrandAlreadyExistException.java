package com.example.application_dev.Exeptions;

public class BrandAlreadyExistException extends Exception {
    public BrandAlreadyExistException(String message) {
        super(message);
    }
}