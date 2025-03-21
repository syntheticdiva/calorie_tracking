package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.exception.InvalidMealDataException;
import com.example.calorie_tracking.exception.MealNotFoundException;
import com.example.calorie_tracking.mapper.MealMapper;
import com.example.calorie_tracking.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с блюдами.
 */
@Service
@RequiredArgsConstructor
public class MealService {

    private MealRepository mealRepository;

    /**
     * Создает новое блюдо на основе предоставленных данных.
     *
     * @param mealDTO DTO с данными для создания блюда
     * @return DTO созданного блюда
     */
    public MealDTO createMeal(MealDTO mealDTO) {
        validateMealData(mealDTO);
        Meal meal = MealMapper.INSTANCE.toEntity(mealDTO);
        Meal savedMeal = mealRepository.save(meal);
        return MealMapper.INSTANCE.toDTO(savedMeal);
    }

    /**
     * Возвращает список всех блюд.
     *
     * @return список DTO всех блюд
     */
    public List<MealDTO> getAllMeals() {
        return mealRepository.findAll().stream()
                .map(MealMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает блюдо по его идентификатору.
     *
     * @param id идентификатор блюда
     * @return DTO найденного блюда
     * @throws MealNotFoundException если блюдо с указанным идентификатором не найдено
     */
    public MealDTO getMealById(Long id) {
        Meal meal = mealRepository.findById(id)
                .orElseThrow(() -> new MealNotFoundException(id));
        return MealMapper.INSTANCE.toDTO(meal);
    }

    /**
     * Проверяет корректность данных блюда.
     *
     * @param mealDTO DTO с данными блюда
     * @throws InvalidMealDataException если данные некорректны
     */
    private void validateMealData(MealDTO mealDTO) {
        if (mealDTO == null) {
            throw new InvalidMealDataException("Данные блюда не могут быть null");
        }
        if (mealDTO.getName() == null || mealDTO.getName().isEmpty()) {
            throw new InvalidMealDataException("Название блюда не может быть пустым");
        }
        if (mealDTO.getCaloriesPerServing() <= 0) {
            throw new InvalidMealDataException("Калории должны быть положительным числом");
        }
    }
}