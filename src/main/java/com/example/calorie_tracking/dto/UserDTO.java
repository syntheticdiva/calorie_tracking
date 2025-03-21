package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Информация о пользователе")
public class UserDTO {
    @Schema(description = "ID пользователя", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Имя", example = "Иван Иванов")
    private String name;

    @Schema(description = "Email", example = "user@example.com")
    private String email;

    @Schema(description = "Возраст", example = "30")
    private int age;

    @Schema(description = "Пол", example = "MALE")
    private String gender;

    @Schema(description = "Вес (кг)", example = "70.5")
    private double weight;

    @Schema(description = "Рост (см)", example = "175.0")
    private double height;

    @Schema(description = "Цель", example = "MAINTENANCE")
    private String goal;

    @Schema(description = "Дневная норма калорий", example = "2000.0", accessMode = Schema.AccessMode.READ_ONLY)
    private double dailyCalorieIntake;
}

