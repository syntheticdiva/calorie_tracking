package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCreateDTO {
    @NotBlank
    private String name;

    @Email
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
}

