package com.example.calorie_tracking.dto;

import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class MealEntryDTO {
    private Long id;
    private Long userId;
    private LocalDate date;
    private List<MealEntryItemDTO> items;
    private Double totalCalories;
}
//@Data
//public class MealEntryDTO {
//    private Long id;
//    private Long userId;
//    private LocalDate date;
//
//    @NotEmpty(message = "Список блюд не может быть пустым")
//    private List<MealEntryMealDTO> meals;
//
//    private Double totalCalories;
//}