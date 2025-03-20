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


@RestController
@RequestMapping("/meal-entries")
public class MealEntryController {

    @Autowired
    private MealEntryService mealEntryService;

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
    @GetMapping("/report")
    public ResponseEntity<DailyReportDTO> getDailyReport(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date
    ) {
        return ResponseEntity.ok(mealEntryService.generateDailyReport(userId, date));
    }
}