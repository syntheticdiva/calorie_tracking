package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealEntryMealDTO {
    private MealDTO meal;
    private int quantity;
}
//@Data
//public class MealEntryMealDTO {
//    @NotNull(message = "Блюдо обязательно")
//    private MealDTO meal;
//
//    @Min(value = 1, message = "Количество порций должно быть не менее 1")
//    private int quantity;
//}