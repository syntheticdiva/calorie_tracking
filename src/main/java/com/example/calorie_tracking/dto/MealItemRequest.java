package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MealItemRequest {
    @NotNull(message = "ID блюда обязательно")
    private Long mealId;

    @Min(value = 1, message = "Количество порций должно быть не менее 1")
    private int quantity;
}