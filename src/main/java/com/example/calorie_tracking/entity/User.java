package com.example.calorie_tracking.entity;

import com.example.calorie_tracking.enums.Gender;
import com.example.calorie_tracking.enums.Goal;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Email(message = "Некорректный формат email")
    @Column(unique = true)
    private String email;

    @Min(value = 1, message = "Возраст должен быть положительным")
    private int age;

    @NotNull(message = "Пол обязателен")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Min(value = 20, message = "Вес должен быть в диапазоне от 20 до 500 кг")
    @Max(value = 500, message = "Вес должен быть в диапазоне от 20 до 500 кг")
    private double weight;

    @Min(value = 50, message = "Рост должен быть в диапазоне от 50 до 250 см")
    @Max(value = 250, message = "Рост должен быть в диапазоне от 50 до 250 см")
    private double height;

    @NotNull(message = "Цель обязательна")
    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(name = "daily_calorie_intake", nullable = false)
    private double dailyCalorieIntake;

    public void calculateDailyCalorieIntake() {
        double bmr;
        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        switch (goal) {
            case WEIGHT_LOSS -> dailyCalorieIntake = bmr * 0.8;
            case MUSCLE_GAIN -> dailyCalorieIntake = bmr * 1.2;
            default -> dailyCalorieIntake = bmr; // MAINTENANCE
        }
    }
}
