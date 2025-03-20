package com.example.calorie_tracking.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
public class MealEntryRequest {
    @NotNull(message = "ID пользователя обязательно")
    private Long userId;

    @NotEmpty(message = "Список блюд не может быть пустым")
    private List<MealItemRequest> meals;

    @NotNull(message = "Дата обязательна")
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
}
