package com.example.calorie_tracking.dto;

import lombok.Data;

@Data
public class MealDTO {
    private Long id;
    private String name;
    private double caloriesPerServing;
    private double proteins;
    private double fats;
    private double carbohydrates;

}
