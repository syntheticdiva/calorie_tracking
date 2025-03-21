package com.example.calorie_tracking.service;

import static org.junit.jupiter.api.Assertions.*;

import com.example.calorie_tracking.dto.DailyReportDTO;
import com.example.calorie_tracking.dto.MealEntryDTO;
import com.example.calorie_tracking.dto.MealEntryItemDTO;
import com.example.calorie_tracking.dto.MealItemRequest;
import com.example.calorie_tracking.entity.*;
import com.example.calorie_tracking.exception.*;
import com.example.calorie_tracking.mapper.MealEntryMapper;
import com.example.calorie_tracking.repository.MealEntryRepository;
import com.example.calorie_tracking.repository.MealRepository;
import com.example.calorie_tracking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MealEntryServiceTest {

    @Mock
    private MealEntryRepository mealEntryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private MealEntryMapper mealEntryMapper;

    @InjectMocks
    private MealEntryService mealEntryService;

    @Captor
    private ArgumentCaptor<MealEntry> mealEntryCaptor;

    private User testUser;
    private Meal testMeal;
    private MealItemRequest validMealItem;
    private MealEntryDTO mealEntryDTO;
    private final LocalDate testDate = LocalDate.now();

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setDailyCalorieIntake(2000);

        testMeal = new Meal();
        testMeal.setId(1L);
        testMeal.setCaloriesPerServing(500);

        validMealItem = new MealItemRequest();
        validMealItem.setMealId(1L);
        validMealItem.setQuantity(2);

        mealEntryDTO = new MealEntryDTO();
        mealEntryDTO.setUserId(1L);
        mealEntryDTO.setDate(testDate);
    }

    @Test
    void createMealEntry_ValidData_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealRepository.findById(1L)).thenReturn(Optional.of(testMeal));
        when(mealEntryRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(mealEntryMapper.toDTO(any())).thenReturn(mealEntryDTO);

        MealEntryDTO result = mealEntryService.createMealEntry(
                1L,
                List.of(validMealItem),
                testDate
        );

        assertNotNull(result);
        verify(mealEntryRepository).save(mealEntryCaptor.capture());

        MealEntry savedEntry = mealEntryCaptor.getValue();
        assertEquals(testUser, savedEntry.getUser());
        assertEquals(testDate, savedEntry.getDate());
        assertEquals(1, savedEntry.getMealEntries().size());
    }

    @Test
    void createMealEntry_UserNotFound_ThrowsException() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> mealEntryService.createMealEntry(1L, List.of(validMealItem), testDate)
        );
    }

    @Test
    void createMealEntry_MealNotFound_ThrowsException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(
                MealNotFoundException.class,
                () -> mealEntryService.createMealEntry(1L, List.of(validMealItem), testDate)
        );
    }

    @Test
    void createMealEntry_EmptyMealItems_ThrowsException() {
        assertThrows(
                InvalidMealEntryDataException.class,
                () -> mealEntryService.createMealEntry(1L, Collections.emptyList(), testDate)
        );
    }

    @Test
    void createMealEntry_InvalidMealItem_ThrowsException() {
        MealItemRequest invalidItem = new MealItemRequest();
        invalidItem.setMealId(null);
        invalidItem.setQuantity(0);

        assertThrows(
                InvalidMealEntryDataException.class,
                () -> mealEntryService.createMealEntry(1L, List.of(invalidItem), testDate)
        );
    }

    @Test
    void getMealEntriesByUserIdAndDate_ReturnsList() {
        MealEntry entry = new MealEntry();
        entry.setUser(testUser);
        entry.setDate(testDate);

        when(mealEntryRepository.findByUserIdAndDate(1L, testDate))
                .thenReturn(List.of(entry));
        when(mealEntryMapper.toDTO(any())).thenReturn(mealEntryDTO);

        List<MealEntryDTO> result = mealEntryService.getMealEntriesByUserIdAndDate(1L, testDate);

        assertEquals(1, result.size());
        verify(mealEntryMapper, atLeastOnce()).toDTO(entry);
    }

    @Test
    void getMealEntriesByUserId_ReturnsList() {
        MealEntry entry = new MealEntry();
        entry.setUser(testUser);

        when(mealEntryRepository.findByUserId(1L)).thenReturn(List.of(entry));
        when(mealEntryMapper.toDTO(any())).thenReturn(mealEntryDTO);

        List<MealEntryDTO> result = mealEntryService.getMealEntriesByUserId(1L);

        assertEquals(1, result.size());
        verify(mealEntryMapper, atLeastOnce()).toDTO(entry);
    }

    @Test
    void generateDailyReport_CalculatesCorrectTotal() {
        MealEntryMeal mealEntryMeal = new MealEntryMeal();
        mealEntryMeal.setMeal(testMeal);
        mealEntryMeal.setQuantity(2);

        MealEntry entry = new MealEntry();
        entry.setUser(testUser);
        entry.setDate(testDate);
        entry.setMealEntries(List.of(mealEntryMeal));

        MealEntryDTO mealEntryDTO = new MealEntryDTO();
        mealEntryDTO.setDate(testDate);
        mealEntryDTO.setTotalCalories(1000.0);
        mealEntryDTO.setItems(List.of(new MealEntryItemDTO()));

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealEntryRepository.findByUserIdAndDate(1L, testDate))
                .thenReturn(List.of(entry));
        when(mealEntryMapper.toDTO(entry)).thenReturn(mealEntryDTO);

        DailyReportDTO report = mealEntryService.generateDailyReport(1L, testDate);

        assertAll(
                () -> assertEquals(testDate, report.getDate()),
                () -> assertEquals(1000.0, report.getTotalCalories(), 0.01),
                () -> assertEquals(1, report.getTotalMeals()),
                () -> assertTrue(report.isWithinLimit()),
                () -> assertFalse(report.getEntries().isEmpty()),
                () -> assertEquals(1, report.getEntries().size())
        );

        verify(mealEntryMapper, times(1)).toDTO(entry);
    }
    @Test
    void generateDailyReport_ExceedsLimit_ReturnsFalse() {
        testUser.setDailyCalorieIntake(500);

        MealEntryMeal mealEntryMeal = new MealEntryMeal();
        mealEntryMeal.setMeal(testMeal);
        mealEntryMeal.setQuantity(2);

        MealEntry entry = new MealEntry();
        entry.setUser(testUser);
        entry.setMealEntries(List.of(mealEntryMeal));

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealEntryRepository.findByUserIdAndDate(1L, testDate))
                .thenReturn(List.of(entry));

        DailyReportDTO report = mealEntryService.generateDailyReport(1L, testDate);

        assertFalse(report.isWithinLimit());
    }
}