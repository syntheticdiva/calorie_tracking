package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Связь блюда с приемом пищи")
public class MealEntryMealDTO {
    @Schema(description = "Информация о блюде", required = true)
    private MealDTO meal;

    @Schema(description = "Количество порций", example = "2", required = true)
    private int quantity;
}
