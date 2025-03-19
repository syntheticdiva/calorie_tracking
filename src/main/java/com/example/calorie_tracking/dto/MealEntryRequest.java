package com.example.calorie_tracking.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class MealEntryRequest {
    private List<Long> mealIds;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
}
