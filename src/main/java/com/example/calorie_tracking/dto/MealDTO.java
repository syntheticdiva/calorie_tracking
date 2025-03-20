package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MealDTO {
    private Long id;

    @NotBlank(message = "Название блюда обязательно")
    private String name;

    @DecimalMin(value = "1.0", message = "Калории на порцию должны быть больше 0")
    private double caloriesPerServing;

    @DecimalMin(value = "0.0", message = "Белки не могут быть отрицательными")
    private double proteins;

    @DecimalMin(value = "0.0", message = "Жиры не могут быть отрицательными")
    private double fats;

    @DecimalMin(value = "0.0", message = "Углеводы не могут быть отрицательными")
    private double carbohydrates;
}