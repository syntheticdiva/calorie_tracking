package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.mapper.MealMapper;
import com.example.calorie_tracking.repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;

    public MealDTO createMeal(MealDTO mealDTO) {
        Meal meal = MealMapper.INSTANCE.toEntity(mealDTO);
        Meal savedMeal = mealRepository.save(meal);
        return MealMapper.INSTANCE.toDTO(savedMeal);
    }

    public List<MealDTO> getAllMeals() {
        return mealRepository.findAll().stream()
                .map(MealMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public MealDTO getMealById(Long id) {
        Meal meal = mealRepository.findById(id).orElseThrow(() -> new RuntimeException("Meal not found"));
        return MealMapper.INSTANCE.toDTO(meal);
    }

    public List<Meal> getAllMealsByIds(List<Long> mealIds) {
        return mealRepository.findAllByIds(mealIds);
    }
}
