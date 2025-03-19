package com.example.calorie_tracking.dto;

import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MealEntryDTO {
    private Long id;
    private Long userId;
    private LocalDate date;
    private List<MealDTO> meals;
    private Double totalCalories;
}