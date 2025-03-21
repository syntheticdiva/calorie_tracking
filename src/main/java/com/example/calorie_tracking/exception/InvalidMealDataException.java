package com.example.calorie_tracking.exception;

public class InvalidMealDataException extends RuntimeException {
    public InvalidMealDataException(String message) {
        super(message);
    }
}
