package com.example.calorie_tracking.service;

import com.example.calorie_tracking.dto.DailyReportDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealItemRequest;
import com.example.calorie_tracking.entity.Meal;
import com.example.calorie_tracking.entity.MealEntry;
import com.example.calorie_tracking.entity.MealEntryMeal;
import com.example.calorie_tracking.entity.User;
import com.example.calorie_tracking.exception.InvalidMealEntryDataException;
import com.example.calorie_tracking.exception.MealNotFoundException;
import com.example.calorie_tracking.exception.UserNotFoundException;
import com.example.calorie_tracking.mapper.MealEntryMapper;
import com.example.calorie_tracking.repository.MealEntryRepository;
import com.example.calorie_tracking.repository.MealRepository;
import com.example.calorie_tracking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Сервис для работы с записями о приемах пищи.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MealEntryService {

    private final MealEntryRepository mealEntryRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;
    private final MealEntryMapper mealEntryMapper;

    /**
     * Создает запись о приеме пищи для указанного пользователя.
     *
     * @param userId    идентификатор пользователя
     * @param mealItems список блюд с их количеством
     * @param date      дата приема пищи
     * @return DTO созданной записи о приеме пищи
     * @throws UserNotFoundException          если пользователь не найден
     * @throws MealNotFoundException          если блюдо не найдено
     */
    @Transactional
    public MealEntryDTO createMealEntry(Long userId, List<MealItemRequest> mealItems, LocalDate date) {
        validateMealItems(mealItems);

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

    /**
     * Возвращает список записей о приемах пищи для указанного пользователя и даты.
     *
     * @param userId идентификатор пользователя
     * @param date  дата приема пищи
     * @return список DTO записей о приемах пищи
     */
    public List<MealEntryDTO> getMealEntriesByUserIdAndDate(Long userId, LocalDate date) {
        return mealEntryRepository.findByUserIdAndDate(userId, date)
                .stream()
                .map(mealEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает список всех записей о приемах пищи для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список DTO записей о приемах пищи
     */
    public List<MealEntryDTO> getMealEntriesByUserId(Long userId) {
        return mealEntryRepository.findByUserId(userId)
                .stream()
                .map(mealEntryMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Генерирует ежедневный отчет о потребленных калориях для указанного пользователя и даты.
     *
     * @param userId идентификатор пользователя
     * @param date  дата отчета
     * @return DTO ежедневного отчета
     */
    public DailyReportDTO generateDailyReport(Long userId, LocalDate date) {
        User user = findUserById(userId);
        List<MealEntry> entries = mealEntryRepository.findByUserIdAndDate(userId, date);
        double totalCalories = calculateTotalCalories(entries);
        return buildDailyReport(date, totalCalories, user, entries);
    }

    /**
     * Находит пользователя по идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return найденный пользователь
     * @throws UserNotFoundException если пользователь не найден
     */
    private User findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    /**
     * Вычисляет общее количество калорий для списка записей о приемах пищи.
     *
     * @param entries список записей о приемах пищи
     * @return общее количество калорий
     */
    private double calculateTotalCalories(List<MealEntry> entries) {
        double total = entries.stream()
                .mapToDouble(MealEntry::getTotalCalories)
                .sum();
        return total;
    }

    /**
     * Создает DTO ежедневного отчета.
     *
     * @param date          дата отчета
     * @param totalCalories общее количество калорий
     * @param user          пользователь
     * @param entries       список записей о приемах пищи
     * @return DTO ежедневного отчета
     */
    private DailyReportDTO buildDailyReport(LocalDate date,
                                            double totalCalories,
                                            User user,
                                            List<MealEntry> entries) {
        List<MealEntryDTO> entriesDTO = entries.stream()
                .map(mealEntryMapper::toDTO)
                .toList();

        return new DailyReportDTO(
                date,
                totalCalories,
                entries.size(),
                totalCalories <= user.getDailyCalorieIntake(),
                entriesDTO
        );
    }

    /**
     * Проверяет корректность данных о приеме пищи.
     *
     * @param mealItems список блюд с их количеством
     * @throws InvalidMealEntryDataException если данные некорректны
     */
    private void validateMealItems(List<MealItemRequest> mealItems) {
        if (mealItems == null || mealItems.isEmpty()) {
            throw new InvalidMealEntryDataException("Список блюд не может быть пустым");
        }
        for (MealItemRequest item : mealItems) {
            if (item.getMealId() == null) {
                throw new InvalidMealEntryDataException("ID блюда не может быть null");
            }
            if (item.getQuantity() <= 0) {
                throw new InvalidMealEntryDataException("Количество блюда должно быть положительным числом");
            }
        }
    }
}