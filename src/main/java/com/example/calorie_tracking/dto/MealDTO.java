package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Информация о блюде")
public class MealDTO {
    @Schema(
            description = "ID блюда",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Название блюда",
            example = "Пшенная каша",
            required = true
    )
    @NotBlank(message = "Название блюда обязательно")
    private String name;

    @Schema(
            description = "Калорийность на порцию (ккал)",
            example = "150.0",
            required = true
    )
    @DecimalMin(
            value = "1.0",
            message = "Калории на порцию должны быть больше 0"
    )
    private double caloriesPerServing;

    @Schema(
            description = "Содержание белков на порцию (г)",
            example = "5.5",
            required = true
    )
    @DecimalMin(
            value = "0.0",
            message = "Белки не могут быть отрицательными"
    )
    private double proteins;

    @Schema(
            description = "Содержание жиров на порцию (г)",
            example = "3.2",
            required = true
    )
    @DecimalMin(
            value = "0.0",
            message = "Жиры не могут быть отрицательными"
    )
    private double fats;

    @Schema(
            description = "Содержание углеводов на порцию (г)",
            example = "25.0",
            required = true
    )
    @DecimalMin(
            value = "0.0",
            message = "Углеводы не могут быть отрицательными"
    )
    private double carbohydrates;
}