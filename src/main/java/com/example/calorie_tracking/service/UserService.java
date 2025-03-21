package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.UserCreateDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.exception.InvalidUserDataException;
import com.example.calorie_tracking.exception.UserNotFoundException;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Сервис для работы с пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Создает нового пользователя на основе предоставленных данных.
     *
     * @param userDTO DTO с данными для создания пользователя
     * @return DTO созданного пользователя
     * @throws InvalidUserDataException если данные пользователя некорректны или email уже существует
     */
    @Transactional
    public UserDTO createUser(UserCreateDTO userDTO) {
        validateUserData(userDTO);

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new InvalidUserDataException("Пользователь с таким адресом электронной почты уже существует");
        }

        User user = userMapper.toEntity(userDTO);
        user.calculateDailyCalorieIntake();
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список DTO всех пользователей
     */
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return DTO найденного пользователя
     * @throws UserNotFoundException если пользователь с указанным идентификатором не найден
     */
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.toDTO(user);
    }

    /**
     * Проверяет корректность данных пользователя.
     *
     * @param dto DTO с данными пользователя
     * @throws InvalidUserDataException если данные некорректны
     */
    private void validateUserData(UserCreateDTO dto) {
        if (dto.getAge() <= 0) {
            throw new InvalidUserDataException("Возраст должен быть положительным");
        }
        if (dto.getWeight() <= 0) {
            throw new InvalidUserDataException("Вес должен быть положительным");
        }
        if (dto.getHeight() <= 0) {
            throw new InvalidUserDataException("Рост должен быть положительным");
        }
    }
}
