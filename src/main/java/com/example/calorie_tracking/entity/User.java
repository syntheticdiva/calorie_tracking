package com.example.calorie_tracking.entity;

import com.example.calorie_tracking.enums.Gender;
import com.example.calorie_tracking.enums.Goal;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import com.vladmihalcea.hibernate.type.basic.PostgreSQLEnumType;
import org.hibernate.annotations.Type;


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

    @Min(value = 1, message = "Вес должен быть положительным")
    private double weight;

    @Min(value = 1, message = "Рост должен быть положительным")
    private double height;

    @NotNull(message = "Цель обязательна")
    @Enumerated(EnumType.STRING)
    private Goal goal;

    @Column(name = "daily_calorie_intake", nullable = false)
    private double dailyCalorieIntake;

    // Метод для расчета калорий
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
//@Data
//@Entity
//@Table(name = "users")
//public class User {
//    @Getter
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank
//    private String name;
//
//    @Email
//    @Column(unique = true)
//    private String email;
//
//    @Min(1)
//    private int age;
//
//    @NotBlank
//    @Column(columnDefinition = "gender_enum")
//    private String gender;
//
//    @Min(1)
//    private double weight;
//
//    @Min(1)
//    private double height;
//
//    @NotBlank
//    @Column(columnDefinition = "goal_enum")
//    private String goal;
//
//    @Column(name = "daily_calorie_intake", nullable = false)
//    private double dailyCalorieIntake;
//
//    public void calculateDailyCalorieIntake() {
//        double bmr;
//        if (gender.equalsIgnoreCase("male")) {
//            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
//        } else {
//            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
//        }
//
//        switch (goal.toLowerCase()) {
//            case "похудение":
//                dailyCalorieIntake = bmr * 0.8;
//                break;
//            case "набор массы":
//                dailyCalorieIntake = bmr * 1.2;
//                break;
//            default:
//                dailyCalorieIntake = bmr;
//                break;
//        }
//    }
//
//}
