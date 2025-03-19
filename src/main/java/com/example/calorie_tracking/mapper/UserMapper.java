package com.example.calorie_tracking.mapper;


import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "dailyCalorieIntake", ignore = true) // Вычисляемое поле
    UserDTO toDTO(User user);

    User toEntity(UserDTO dto);
}