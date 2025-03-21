package com.example.calorie_tracking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Schema(description = "Запрос на добавление приема пищи")
public class MealEntryRequest {
    @Schema(description = "ID пользователя", example = "1", required = true)
    @NotNull(message = "ID пользователя обязательно")
    private Long userId;

    @Schema(description = "Список блюд", required = true)
    @NotEmpty(message = "Список блюд не может быть пустым")
    private List<MealItemRequest> meals;

    @Schema(description = "Дата приема пищи (dd.MM.yyyy)", example = "20.03.2024", required = true)
    @NotNull(message = "Дата обязательна")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;
}
