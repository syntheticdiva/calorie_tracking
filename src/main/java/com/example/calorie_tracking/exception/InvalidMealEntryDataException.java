package com.example.calorie_tracking.exception;

public class InvalidMealEntryDataException extends RuntimeException {
    public InvalidMealEntryDataException(String message) {
        super(message);
    }
}
