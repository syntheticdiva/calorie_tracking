package com.example.calorie_tracking.repository;

import com.example.calorie_tracking.entity.MealEntryMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealEntryMealRepository extends JpaRepository<MealEntryMeal, Long> {
    @Query("""
        SELECT me.date, SUM(mem.quantity * m.caloriesPerServing) 
        FROM MealEntry me
        JOIN me.mealEntries mem
        JOIN mem.meal m
        WHERE me.user.id = :userId
        GROUP BY me.date
    """)
    List<Object[]> findDailyCaloriesSummary(@Param("userId") Long userId);
}