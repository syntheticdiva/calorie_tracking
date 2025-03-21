package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.entity.Meal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MealMapper {
    MealMapper INSTANCE = Mappers.getMapper(MealMapper.class);

    // Убрали аннотацию с игнорированием quantity
    MealDTO toDTO(Meal meal);

    Meal toEntity(MealDTO dto);

    List<MealDTO> toMealDTOList(List<Meal> meals);
}