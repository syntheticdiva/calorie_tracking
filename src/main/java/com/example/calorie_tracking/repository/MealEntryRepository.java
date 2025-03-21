package com.example.calorie_tracking.repository;

import com.example.calorie_tracking.entity.MealEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MealEntryRepository extends JpaRepository<MealEntry, Long> {
    List<MealEntry> findByUserIdAndDate(Long userId, LocalDate date);
    List<MealEntry> findByUserId(Long userId);
}
