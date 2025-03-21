package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MealMapper {
    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    MealDTO toDTO(Meal meal);

    Meal toEntity(MealDTO dto);
}