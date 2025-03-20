package com.example.calorie_tracking.mapper;

import com.example.calorie_tracking.dto.MealDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealEntryItemDTO;
import com.example.calorie_tracking.dto.MealEntryMealDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.MealEntry;
import com.example.calorie_tracking.entity.MealEntryMeal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;
@Mapper(componentModel = "spring", uses = {MealMapper.class})
public interface MealEntryMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "items", source = "mealEntries")
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
//@Mapper(uses = {MealMapper.class, UserMapper.class})
//public interface MealEntryMapper {
//    MealEntryMapper INSTANCE = Mappers.getMapper(MealEntryMapper.class);
//
//    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "mealEntries", target = "meals")
//    @Mapping(target = "totalCalories", expression = "java(mealEntry.getTotalCalories())")
//    MealEntryDTO toDTO(MealEntry mealEntry);
//
//    @Mapping(target = "user", ignore = true)
//    @Mapping(target = "mealEntries", ignore = true)
//    MealEntry toEntity(MealEntryDTO mealEntryDTO);
//
//    default List<MealDTO> mapMealEntriesToMealDTOs(List<MealEntryMeal> mealEntries) {
//        return mealEntries.stream()
//                .map(me -> {
//                    MealDTO dto = MealMapper.INSTANCE.toDTO(me.getMeal());
//                    dto.setQuantity(me.getQuantity()); // Добавляем количество порций
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }
//}