package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.UserCreateDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // Lombok сгенерирует конструктор
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper; // Внедрение через конструктор

    @Transactional
    public UserDTO createUser(UserCreateDTO userDTO) {
        validateUserData(userDTO);
        User user = userMapper.toEntity(userDTO);
        user.calculateDailyCalorieIntake();
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser); // Используем бин, а не INSTANCE
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO) // Используем бин
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user); // Используем бин
    }
    private void validateUserData(UserCreateDTO dto) {
        if (dto.getAge() <= 0) {
            throw new IllegalArgumentException("Возраст должен быть положительным");
        }
        if (dto.getWeight() <= 0) {
            throw new IllegalArgumentException("Вес должен быть положительным");
        }
        if (dto.getHeight() <= 0) {
            throw new IllegalArgumentException("Рост должен быть положительным");
        }
    }
}