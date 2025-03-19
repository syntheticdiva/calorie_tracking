package com.example.calorie_tracking.exception;

public class ConfigFileNotFoundException extends RuntimeException {
    public ConfigFileNotFoundException(String message) {
        super(message);
    }
}

