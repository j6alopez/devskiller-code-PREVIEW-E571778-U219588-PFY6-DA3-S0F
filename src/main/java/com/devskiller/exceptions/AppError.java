package com.devskiller.exceptions;

public class AppError extends RuntimeException {
    public AppError(String message) {
        super(message);
    }
}
