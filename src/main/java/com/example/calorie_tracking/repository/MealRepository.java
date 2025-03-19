package com.example.calorie_tracking.repository;

import com.example.calorie_tracking.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MealRepository extends JpaRepository<Meal, Long> {
    @Query("SELECT m FROM Meal m WHERE m.id IN :mealIds")
    List<Meal> findAllByIds(@Param("mealIds") List<Long> mealIds);
}
