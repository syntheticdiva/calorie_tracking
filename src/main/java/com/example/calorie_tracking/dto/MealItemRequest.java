package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Запрос на добавление блюда в прием пищи")
public class MealItemRequest {
    @Schema(description = "ID блюда", example = "1", required = true)
    @NotNull(message = "ID блюда обязательно")
    private Long mealId;

    @Schema(description = "Количество порций (мин. 1)", example = "2", required = true)
    @Min(value = 1, message = "Количество порций должно быть не менее 1")
    private int quantity;
}