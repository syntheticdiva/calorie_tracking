package com.example.calorie_tracking.exception;

public class MealNotFoundException extends RuntimeException {
    public MealNotFoundException(Long mealId) {
        super("Блюдо с ID " + mealId + " не найдено");
    }
}
