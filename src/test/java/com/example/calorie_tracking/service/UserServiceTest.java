package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.UserCreateDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.enums.Goal;
import com.example.calorie_tracking.exception.InvalidUserDataException;
import com.example.calorie_tracking.exception.UserNotFoundException;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserCreateDTO validUserCreateDTO;
    private User userEntity;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        validUserCreateDTO = new UserCreateDTO();
        validUserCreateDTO.setName("Test User");
        validUserCreateDTO.setEmail("test@example.com");
        validUserCreateDTO.setAge(30);
        validUserCreateDTO.setGender("MALE");
        validUserCreateDTO.setWeight(70.0);
        validUserCreateDTO.setHeight(175.0);
        validUserCreateDTO.setGoal("MAINTENANCE");

        userEntity = new User();
        userEntity.setId(1L);
        userEntity.setName("Test User");
        userEntity.setEmail("test@example.com");
        userEntity.setAge(30);
        userEntity.setGender(com.example.calorie_tracking.enums.Gender.MALE);
        userEntity.setWeight(70.0);
        userEntity.setHeight(175.0);
        userEntity.setGoal(com.example.calorie_tracking.enums.Goal.MAINTENANCE);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setAge(30);
        userDTO.setGender("MALE");
        userDTO.setWeight(70.0);
        userDTO.setHeight(175.0);
        userDTO.setGoal("MAINTENANCE");
        userDTO.setDailyCalorieIntake(2000.0);
    }

    @Test
    void createUser_WithValidData_ReturnsUserDTO() {
        when(userMapper.toEntity(validUserCreateDTO)).thenReturn(userEntity);
        when(userRepository.existsByEmail(validUserCreateDTO.getEmail())).thenReturn(false);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO result = userService.createUser(validUserCreateDTO);

        assertNotNull(result);
        assertEquals(userDTO.getId(), result.getId());
        assertEquals(userDTO.getDailyCalorieIntake(), result.getDailyCalorieIntake());
        verify(userRepository).existsByEmail(validUserCreateDTO.getEmail());
        verify(userRepository).save(userEntity);
        verify(userMapper).toDTO(userEntity);
    }

    @Test
    void createUser_WithExistingEmail_ThrowsException() {
        when(userRepository.existsByEmail(validUserCreateDTO.getEmail())).thenReturn(true);

        assertThrows(InvalidUserDataException.class,
                () -> userService.createUser(validUserCreateDTO));

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_WithInvalidAge_ThrowsException() {
        validUserCreateDTO.setAge(0);

        assertThrows(InvalidUserDataException.class,
                () -> userService.createUser(validUserCreateDTO));
    }

    @Test
    void createUser_WithInvalidWeight_ThrowsException() {
        validUserCreateDTO.setWeight(0);

        assertThrows(InvalidUserDataException.class,
                () -> userService.createUser(validUserCreateDTO));
    }

    @Test
    void createUser_WithInvalidHeight_ThrowsException() {
        validUserCreateDTO.setHeight(0);

        assertThrows(InvalidUserDataException.class,
                () -> userService.createUser(validUserCreateDTO));
    }

    @Test
    void getAllUsers_ReturnsListOfUserDTOs() {
        List<User> users = List.of(userEntity);
        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDTO.getId(), result.get(0).getId());
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_WhenEmpty_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<UserDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getUserById_WithValidId_ReturnsUserDTO() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userDTO);

        UserDTO result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserById_WithInvalidId_ThrowsException() {
        Long invalidId = 999L;
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.getUserById(invalidId));
    }
}