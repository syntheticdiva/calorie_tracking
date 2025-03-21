package com.example.calorie_tracking.dto;

import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Schema(description = "Информация о приеме пищи")
public class MealEntryDTO {
    @Schema(description = "ID записи", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID пользователя", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long userId;

    @Schema(description = "Дата приема пищи", example = "2024-03-20")
    private LocalDate date;

    @Schema(description = "Список блюд")
    private List<MealEntryItemDTO> items;

    @Schema(description = "Общее количество калорий", example = "500.0", accessMode = Schema.AccessMode.READ_ONLY)
    private Double totalCalories;
}