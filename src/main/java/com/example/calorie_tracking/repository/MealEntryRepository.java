package com.example.calorie_tracking.repository;

import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.MealEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealEntryRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByUserIdAndDate(Long userId, LocalDate date);
    List<MealEntry> findByUserId(Long userId);
//    @Query("SELECT me.date, SUM(mem.quantity * m.caloriesPerServing) " +
//            "FROM MealEntry me " +
//            "JOIN me.mealEntries mem " +        // mealEntries - список MealEntryMeal в MealEntry
//            "JOIN mem.meal m " +                // mem.meal - связь MealEntryMeal -> Meal
//            "WHERE me.user.id = :userId " +
//            "GROUP BY me.date")
//    List<Object[]> findDailyCaloriesSummary(@Param("userId") Long userId);

}
