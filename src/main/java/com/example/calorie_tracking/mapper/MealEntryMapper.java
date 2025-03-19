package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.MealEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {MealMapper.class})
public interface MealEntryMapper {
    MealEntryMapper INSTANCE = Mappers.getMapper(MealEntryMapper.class);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "meals", target = "meals")  // Используем прямое соответствие имен
    @Mapping(target = "totalCalories", expression = "java(mealEntry.getTotalCalories())")
    MealEntryDTO toDTO(MealEntry mealEntry);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "meals", ignore = true)
    MealEntry toEntity(MealEntryDTO mealEntryDTO);
}