package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealEntryItemDTO;
import com.example.calorie_tracking.entity.MealEntry;
import com.example.calorie_tracking.entity.MealEntryMeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
@Mapper(componentModel = "spring", uses = {MealMapper.class})
public interface MealEntryMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "mealEntries")
    @Mapping(target = "totalCalories", expression = "java(mealEntry.getTotalCalories())")
    MealEntryDTO toDTO(MealEntry mealEntry);

    default List<MealEntryItemDTO> mapMealEntries(List<MealEntryMeal> mealEntries) {
        return mealEntries.stream()
                .map(me -> new MealEntryItemDTO(
                        MealMapper.INSTANCE.toDTO(me.getMeal()),
                        me.getQuantity()
                ))
                .toList();
    }
}
