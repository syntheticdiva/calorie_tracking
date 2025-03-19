package com.example.calorie_tracking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "users")
public class User {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @Column(unique = true)
    private String email;

    @Min(1)
    private int age;

    @NotBlank
    private String gender;

    @Min(1)
    private double weight;

    @Min(1)
    private double height;

    @NotBlank
    private String goal;

    @Column(name = "daily_calorie_intake", nullable = false)
    private double dailyCalorieIntake;

    public void calculateDailyCalorieIntake() {
        double bmr;
        if (gender.equalsIgnoreCase("male")) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        switch (goal.toLowerCase()) {
            case "похудение":
                dailyCalorieIntake = bmr * 0.8;
                break;
            case "набор массы":
                dailyCalorieIntake = bmr * 1.2;
                break;
            default:
                dailyCalorieIntake = bmr;
                break;
        }
    }

}
