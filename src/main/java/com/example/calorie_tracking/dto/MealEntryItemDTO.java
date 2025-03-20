package com.example.calorie_tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MealEntryItemDTO {
    private MealDTO meal;
    private int quantity;
}
