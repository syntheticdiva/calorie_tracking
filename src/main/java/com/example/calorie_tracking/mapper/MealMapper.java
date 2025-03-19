package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MealMapper {
    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    MealDTO toDTO(Meal meal);
    Meal toEntity(MealDTO dto);

    // Явное указание маппинга для списков
    List<MealDTO> toMealDTOList(List<Meal> meals);
    List<Meal> toMealList(List<MealDTO> dtos);
}