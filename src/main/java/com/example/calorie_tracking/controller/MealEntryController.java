package com.example.calorie_tracking.controller;

import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.service.MealEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/meal-entries")
public class MealEntryController {

    @Autowired
    private MealEntryService mealEntryService;

    @PostMapping
    public ResponseEntity<MealEntryDTO> createMealEntry(
            @RequestParam Long userId,
            @RequestParam List<Long> mealIds,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return ResponseEntity.ok(mealEntryService.createMealEntry(userId, mealIds, date));
    }

    @GetMapping("/daily")
    public ResponseEntity<List<MealEntryDTO>> getDailyMealEntries(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return ResponseEntity.ok(mealEntryService.getMealEntriesByUserIdAndDate(userId, date));
    }

    @GetMapping("/history")
    public ResponseEntity<List<MealEntryDTO>> getMealHistory(@RequestParam Long userId) {
        return ResponseEntity.ok(mealEntryService.getMealEntriesByUserId(userId));
    }
}