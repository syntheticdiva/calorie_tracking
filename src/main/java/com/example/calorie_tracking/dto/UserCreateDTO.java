package com.example.calorie_tracking.dto;

import com.example.calorie_tracking.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на создание пользователя")
public class UserCreateDTO {
    @Schema(description = "Имя", example = "Иван Иванов", required = true)
    @NotBlank(message = "Имя обязательно")
    private String name;

    @Schema(description = "Email", example = "user@example.com", required = true)
    @Email(message = "Некорректный email")
    private String email;

    @Schema(description = "Возраст", example = "30", required = true)
    @Min(value = 1, message = "Возраст должен быть положительным")
    private int age;
    @Schema(
            description = "Пол",
            example = "MALE, FEMALE",
            allowableValues = {"MALE", "FEMALE"},
            implementation = Gender.class,
            required = true
    )
    @NotBlank(message = "Пол обязателен")
    private String gender;

    @Schema(description = "Вес (кг)", example = "70.5", required = true)
    @Min(value = 20, message = "Вес должен быть в диапазоне от 20 до 500 кг")
    @Max(value = 500, message = "Вес должен быть в диапазоне от 20 до 500 кг")
    private double weight;

    @Schema(description = "Рост (см)", example = "175.0", required = true)
    @Min(value = 50, message = "Рост должен быть в диапазоне от 50 до 250 см")
    @Max(value = 250, message = "Рост должен быть в диапазоне от 50 до 250 см")
    private double height;

    @Schema(description = "Цель", example = "WEIGHT_LOSS, MAINTENANCE, MUSCLE_GAIN",
            allowableValues = {"WEIGHT_LOSS", "MAINTENANCE", "MUSCLE_GAIN"}, required = true)
    @NotBlank(message = "Цель обязательна")
    private String goal;
}

