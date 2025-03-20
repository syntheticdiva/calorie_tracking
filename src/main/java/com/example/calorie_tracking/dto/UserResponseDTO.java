package com.example.calorie_tracking.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private int age;
    private String gender;
    private double weight;
    private double height;
    private String goal;
    private double dailyCalorieIntake;
}