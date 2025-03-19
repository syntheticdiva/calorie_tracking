package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.INSTANCE.toEntity(userDTO);
        user.calculateDailyCalorieIntake();
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.toDTO(savedUser);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.INSTANCE.toDTO(user);
    }
}
