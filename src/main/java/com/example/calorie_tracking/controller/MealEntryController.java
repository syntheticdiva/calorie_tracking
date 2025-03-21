package com.example.calorie_tracking.controller;

import com.example.calorie_tracking.dto.DailyReportDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealEntryRequest;
import com.example.calorie_tracking.service.MealEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


/**
 * Контроллер для работы с записями о приемах пищи.
 * Предоставляет методы для создания и получения записей о питании пользователей.
 */
@RestController
@RequestMapping("/meal-entries")
public class MealEntryController {

    @Autowired
    private MealEntryService mealEntryService;

    /**
     * Создает новую запись о приеме пищи.
     *
     * @param request запрос с данными о приеме пищи
     * @return ResponseEntity с созданной записью в формате MealEntryDTO
     */
    @PostMapping
    public ResponseEntity<MealEntryDTO> createMealEntry(@Valid @RequestBody MealEntryRequest request) {
        return ResponseEntity.ok(
                mealEntryService.createMealEntry(
                        request.getUserId(),
                        request.getMeals(),
                        request.getDate()
                )
        );
    }

    /**
     * Получает записи о приемах пищи за указанную дату.
     *
     * @param userId идентификатор пользователя
     * @param date   дата в формате dd.MM.yyyy (например: 20.03.2024)
     * @return список записей о приемах пищи с детализацией по блюдам
     */
    @GetMapping("/daily")
    public ResponseEntity<List<MealEntryDTO>> getDailyMealEntries(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return ResponseEntity.ok(mealEntryService.getMealEntriesByUserIdAndDate(userId, date));
    }

    /**
     * Получает полную историю приемов пищи пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список всех записей о приемах пищи пользователя
     */
    @GetMapping("/history")
    public ResponseEntity<List<MealEntryDTO>> getMealHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(mealEntryService.getMealEntriesByUserId(userId));
    }

    /**
     * Генерирует дневной отчет по калориям.
     *
     * @param userId идентификатор пользователя
     * @param date   дата в формате dd.MM.yyyy (например: 20.03.2024)
     * @return отчет с суммарными показателями и списком приемов пищи
     */
    @GetMapping("/report")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return ResponseEntity.ok(mealEntryService.generateDailyReport(userId, date));
    }
}