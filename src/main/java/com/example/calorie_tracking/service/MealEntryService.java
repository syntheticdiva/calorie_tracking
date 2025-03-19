package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.MealEntry;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.mapper.MealEntryMapper;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.MealEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealEntryService {

    @Autowired
    private MealEntryRepository mealEntryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MealService mealService;

    public MealEntryDTO createMealEntry(Long userId, List<Long> mealIds, LocalDate date) {
        // Получаем UserDTO
        UserDTO userDTO = userService.getUserById(userId);

        // Преобразуем UserDTO в User с помощью маппера
        User user = UserMapper.INSTANCE.toEntity(userDTO);

        // Создаем MealEntry
        MealEntry mealEntry = new MealEntry();
        mealEntry.setUser(user); // Теперь передаем объект типа User
        mealEntry.setDate(date);
        mealEntry.setMeals(mealService.getAllMealsByIds(mealIds));

        // Сохраняем MealEntry
        MealEntry savedMealEntry = mealEntryRepository.save(mealEntry);

        // Преобразуем сохраненный MealEntry в DTO
        return MealEntryMapper.INSTANCE.toDTO(savedMealEntry);
    }
    public List<MealEntryDTO> getMealEntriesByUserIdAndDate(Long userId, LocalDate date) {
        return mealEntryRepository.findByUserIdAndDate(userId, date).stream()
                .map(MealEntryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public List<MealEntryDTO> getMealEntriesByUserId(Long userId) {
        return mealEntryRepository.findByUserId(userId).stream()
                .map(MealEntryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }
}
