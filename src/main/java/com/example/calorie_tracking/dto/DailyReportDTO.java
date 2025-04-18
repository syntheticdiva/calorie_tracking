package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Дневной отчет по калориям")
public class DailyReportDTO {
    @Schema(description = "Дата отчета", example = "2024-03-20", required = true)
    private LocalDate date;

    @Schema(description = "Суммарные калории", example = "1500.0", required = true)
    private double totalCalories;

    @Schema(description = "Общее количество приемов пищи", example = "3", required = true)
    private int totalMeals;

    @Schema(description = "Уложился в норму", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private boolean isWithinLimit;

    @Schema(description = "Список приемов пищи", required = true)
    @NotEmpty(message = "Список приемов пищи не может быть пустым")
    private List<MealEntryDTO> entries;
}