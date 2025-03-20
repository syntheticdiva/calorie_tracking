package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.DailyReportDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealItemRequest;
import com.example.calorie_tracking.dto.UserDTO;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.MealEntry;
import com.example.calorie_tracking.entity.MealEntryMeal;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.exception.MealNotFoundException;
import com.example.calorie_tracking.exception.UserNotFoundException;
import com.example.calorie_tracking.mapper.MealEntryMapper;
import com.example.calorie_tracking.mapper.UserMapper;
import com.example.calorie_tracking.repository.MealEntryRepository;
import com.example.calorie_tracking.repository.MealRepository;
import com.example.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MealEntryService {

    private final MealEntryRepository mealEntryRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealEntryMapper mealEntryMapper;

    @Transactional
    public MealEntryDTO createMealEntry(Long userId, List<MealItemRequest> mealItems, LocalDate date) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        MealEntry mealEntry = new MealEntry();
        mealEntry.setUser(user);
        mealEntry.setDate(date);

        List<MealEntryMeal> entries = mealItems.stream()
                .map(item -> {
                    Meal meal = mealRepository.findById(item.getMealId())
                            .orElseThrow(() -> new MealNotFoundException(item.getMealId()));

                    MealEntryMeal entry = new MealEntryMeal();
                    entry.setMealEntry(mealEntry);
                    entry.setMeal(meal);
                    entry.setQuantity(item.getQuantity());
                    return entry;
                })
                .toList();

        mealEntry.setMealEntries(entries);
        MealEntry saved = mealEntryRepository.save(mealEntry);

        return mealEntryMapper.toDTO(saved);
    }
    public List<MealEntryDTO> getMealEntriesByUserIdAndDate(Long userId, LocalDate date) {
        return mealEntryRepository.findByUserIdAndDate(userId, date)
                .stream()
                .map(mealEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MealEntryDTO> getMealEntriesByUserId(Long userId) {
        return mealEntryRepository.findByUserId(userId)
                .stream()
                .map(mealEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DailyReportDTO generateDailyReport(Long userId, LocalDate date) {
        User user = findUserById(userId);
        List<MealEntry> entries = mealEntryRepository.findByUserIdAndDate(userId, date);
        double totalCalories = calculateTotalCalories(entries);
        return buildDailyReport(date, totalCalories, user, entries);
    }

    public Map<LocalDate, Double> getCaloriesHistory(Long userId) {
        return mealEntryRepository.findDailyCaloriesSummary(userId)
                .stream()
                .collect(Collectors.toMap(
                        arr -> (LocalDate) arr[0],
                        arr -> (Double) arr[1]
                ));
    }

    // Вспомогательные методы
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    private Meal findMealById(Long mealId) {
        return mealRepository.findById(mealId)
                .orElseThrow(() -> new MealNotFoundException(mealId));
    }

    private MealEntry createMealEntry(User user, LocalDate date) {
        MealEntry mealEntry = new MealEntry();
        mealEntry.setUser(user);
        mealEntry.setDate(date);
        return mealEntry;
    }

    private void linkMealEntryMeals(MealEntry mealEntry, List<MealItemRequest> mealItems) {
        List<MealEntryMeal> mealEntries = mealItems.stream()
                .map(item -> {
                    Meal meal = findMealById(item.getMealId());
                    return createMealEntryMeal(mealEntry, meal, item.getQuantity());
                })
                .toList();
        mealEntry.setMealEntries(mealEntries);
    }

    private MealEntryMeal createMealEntryMeal(MealEntry mealEntry, Meal meal, int quantity) {
        MealEntryMeal mealEntryMeal = new MealEntryMeal();
        mealEntryMeal.setMealEntry(mealEntry);
        mealEntryMeal.setMeal(meal);
        mealEntryMeal.setQuantity(quantity);
        return mealEntryMeal;
    }

    private double calculateTotalCalories(List<MealEntry> entries) {
        return entries.stream()
                .mapToDouble(MealEntry::getTotalCalories)
                .sum();
    }

    private DailyReportDTO buildDailyReport(LocalDate date, double totalCalories, User user, List<MealEntry> entries) {
        return new DailyReportDTO(
                date,
                totalCalories,
                totalCalories <= user.getDailyCalorieIntake(),
                entries.stream()
                        .map(mealEntryMapper::toDTO)
                        .toList()
        );
    }
}
//@Service
//public class MealEntryService {
//
//    @Autowired
//    private MealEntryRepository mealEntryRepository;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private MealService mealService;
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private MealRepository mealRepository;
//
//    public MealEntryDTO createMealEntry(Long userId, List<MealItemRequest> mealItems, LocalDate date) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        MealEntry mealEntry = new MealEntry();
//        mealEntry.setUser(user);
//        mealEntry.setDate(date);
//
//        // Добавляем блюда с количеством
//        List<MealEntryMeal> mealEntries = mealItems.stream()
//                .map(item -> {
//                    Meal meal = mealRepository.findById(item.getMealId())
//                            .orElseThrow(() -> new RuntimeException("Meal not found"));
//
//                    MealEntryMeal mealEntryMeal = new MealEntryMeal();
//                    mealEntryMeal.setMealEntry(mealEntry);
//                    mealEntryMeal.setMeal(meal);
//                    mealEntryMeal.setQuantity(item.getQuantity());
//                    return mealEntryMeal;
//                })
//                .toList();
//
//        mealEntry.setMealEntries(mealEntries);
//
//        MealEntry savedEntry = mealEntryRepository.save(mealEntry);
//        return MealEntryMapper.INSTANCE.toDTO(savedEntry);
//    }
//    public List<MealEntryDTO> getMealEntriesByUserIdAndDate(Long userId, LocalDate date) {
//        return mealEntryRepository.findByUserIdAndDate(userId, date).stream()
//                .map(MealEntryMapper.INSTANCE::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    public List<MealEntryDTO> getMealEntriesByUserId(Long userId) {
//        return mealEntryRepository.findByUserId(userId).stream()
//                .map(MealEntryMapper.INSTANCE::toDTO)
//                .collect(Collectors.toList());
//    }
//    public DailyReportDTO generateDailyReport(Long userId, LocalDate date) {
//        List<MealEntry> entries = mealEntryRepository.findByUserIdAndDate(userId, date);
//        double totalCalories = entries.stream()
//                .mapToDouble(MealEntry::getTotalCalories)
//                .sum();
//
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        return new DailyReportDTO(
//                date,
//                totalCalories,
//                totalCalories <= user.getDailyCalorieIntake(),
//                entries.stream()
//                        .map(MealEntryMapper.INSTANCE::toDTO)
//                        .toList()
//        );
//    }
//    public Map<LocalDate, Double> getCaloriesHistory(Long userId) {
//        return mealEntryRepository.findDailyCaloriesSummary(userId).stream()
//                .collect(Collectors.toMap(
//                        arr -> (LocalDate) arr[0],
//                        arr -> (Double) arr[1]
//                ));
//    }
//}
