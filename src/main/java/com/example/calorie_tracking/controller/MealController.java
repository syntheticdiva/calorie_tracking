package com.example.calorie_tracking.controller;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с блюдами в системе отслеживания калорий.
 */
@RestController
@RequestMapping("/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    /**
     * Создает новое блюдо в системе.
     *
     * @param mealDTO DTO-объект с данными о блюде
     * @return ResponseEntity с созданным объектом MealDTO и статусом 200 OK
     */
    @PostMapping
    public ResponseEntity<MealDTO> createMeal(@RequestBody MealDTO mealDTO) {
        return ResponseEntity.ok(mealService.createMeal(mealDTO));
    }

    /**
     * Получает список всех блюд в системе.
     *
     * @return ResponseEntity со списком MealDTO и статусом 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<MealDTO>> getAllMeals() {
        return ResponseEntity.ok(mealService.getAllMeals());
    }

    /**
     * Получает блюдо по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор блюда
     * @return ResponseEntity с объектом MealDTO и статусом 200 OK
     */
    @GetMapping("/{id}")
    public ResponseEntity<MealDTO> getMealById(@PathVariable Long id) {
        return ResponseEntity.ok(mealService.getMealById(id));
    }
}