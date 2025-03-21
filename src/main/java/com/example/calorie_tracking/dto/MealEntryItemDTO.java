package com.example.calorie_tracking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Блюдо и его количество в приеме пищи")
public class MealEntryItemDTO {
    @Schema(description = "Информация о блюде", required = true)
    private MealDTO meal;

    @Schema(description = "Количество порций", example = "2", required = true)
    private int quantity;
}