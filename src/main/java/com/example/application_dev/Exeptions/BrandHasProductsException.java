package com.example.application_dev.Exeptions;

public class BrandHasProductsException extends RuntimeException {
    public BrandHasProductsException(String message) {
        super(message);
    }
}

