package com.example.calorie_tracking.controller;

import com.example.calorie_tracking.dto.UserCreateDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для управления пользователями системы.
 * Обеспечивает создание и получение информации о пользователях.
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Регистрирует нового пользователя в системе.
     *
     * @param userCreateDTO DTO с данными для создания пользователя
     * @return созданный пользователь в формате UserDTO
     */
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return ResponseEntity.ok(userService.createUser(userCreateDTO));
    }

    /**
     * Получает список всех зарегистрированных пользователей.
     *
     * @return список пользователей в формате UserDTO.
     */
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Находит пользователя по уникальному идентификатору.
     *
     * @param id уникальный ID пользователя
     * @return данные пользователя в формате UserDTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}