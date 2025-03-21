package com.example.calorie_tracking.mapper;


import com.example.calorie_tracking.dto.UserCreateDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.enums.Gender;
import com.example.calorie_tracking.enums.Goal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "goal", source = "goal")
    @Mapping(target = "dailyCalorieIntake", ignore = true)
    User toEntity(UserCreateDTO dto);

    @Mapping(source = "dailyCalorieIntake", target = "dailyCalorieIntake")
    UserDTO toDTO(User user);

    default Gender mapGender(String gender) {
        return Gender.valueOf(gender.toUpperCase());
    }

    default Goal mapGoal(String goal) {
        return Goal.valueOf(goal.toUpperCase());
    }
}