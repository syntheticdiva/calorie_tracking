package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.exception.InvalidMealDataException;
import com.example.calorie_tracking.exception.MealNotFoundException;
import com.example.calorie_tracking.mapper.MealMapper;
import com.example.calorie_tracking.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    private Meal validMeal;
    private MealDTO validMealDTO;

    @BeforeEach
    void setUp() {
        validMeal = new Meal();
        validMeal.setId(1L);
        validMeal.setName("Test Meal");
        validMeal.setCaloriesPerServing(200.0);
        validMeal.setProteins(10.0);
        validMeal.setFats(5.0);
        validMeal.setCarbohydrates(30.0);

        validMealDTO = new MealDTO();
        validMealDTO.setName("Test Meal");
        validMealDTO.setCaloriesPerServing(200.0);
        validMealDTO.setProteins(10.0);
        validMealDTO.setFats(5.0);
        validMealDTO.setCarbohydrates(30.0);
    }

    // Тесты для createMeal
    @Test
    void createMeal_WithValidData_ReturnsSavedMealDTO() {
        when(mealRepository.save(any(Meal.class))).thenReturn(validMeal);

        MealDTO result = mealService.createMeal(validMealDTO);

        assertNotNull(result);
        assertEquals(validMeal.getId(), result.getId());
        assertEquals(validMeal.getName(), result.getName());
        assertEquals(validMeal.getCaloriesPerServing(), result.getCaloriesPerServing());

        verify(mealRepository).save(any(Meal.class));
    }

    @Test
    void createMeal_WithEmptyName_ThrowsInvalidMealDataException() {
        validMealDTO.setName("");

        assertThrows(InvalidMealDataException.class,
                () -> mealService.createMeal(validMealDTO));
        verifyNoInteractions(mealRepository);
    }

    @Test
    void createMeal_WithZeroCalories_ThrowsInvalidMealDataException() {
        validMealDTO.setCaloriesPerServing(0);

        assertThrows(InvalidMealDataException.class,
                () -> mealService.createMeal(validMealDTO));
        verifyNoInteractions(mealRepository);
    }

    @Test
    void createMeal_WithNullDTO_ThrowsInvalidMealDataException() {
        assertThrows(InvalidMealDataException.class,
                () -> mealService.createMeal(null));
        verifyNoInteractions(mealRepository);
    }

    // Тесты для getAllMeals
    @Test
    void getAllMeals_WhenMealsExist_ReturnsListOfDTOs() {
        List<Meal> meals = Arrays.asList(validMeal, new Meal());
        when(mealRepository.findAll()).thenReturn(meals);

        List<MealDTO> result = mealService.getAllMeals();

        assertEquals(meals.size(), result.size());
        verify(mealRepository).findAll();
    }

    @Test
    void getAllMeals_WhenNoMeals_ReturnsEmptyList() {
        when(mealRepository.findAll()).thenReturn(Collections.emptyList());

        List<MealDTO> result = mealService.getAllMeals();

        assertTrue(result.isEmpty());
        verify(mealRepository).findAll();
    }

    // Тесты для getMealById
    @Test
    void getMealById_WithExistingId_ReturnsMealDTO() {
        when(mealRepository.findById(validMeal.getId())).thenReturn(Optional.of(validMeal));

        MealDTO result = mealService.getMealById(validMeal.getId());

        assertNotNull(result);
        assertEquals(validMeal.getId(), result.getId());
        verify(mealRepository).findById(validMeal.getId());
    }

    @Test
    void getMealById_WithNonExistingId_ThrowsMealNotFoundException() {
        Long nonExistingId = 999L;
        when(mealRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(MealNotFoundException.class,
                () -> mealService.getMealById(nonExistingId));
        verify(mealRepository).findById(nonExistingId);
    }
}