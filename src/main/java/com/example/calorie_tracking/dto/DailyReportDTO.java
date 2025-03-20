package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class DailyReportDTO {
    private LocalDate date;
    private double totalCalories;
    private boolean isWithinLimit;

    @NotEmpty(message = "Список приемов пищи не может быть пустым")
    private List<MealEntryDTO> entries;
}