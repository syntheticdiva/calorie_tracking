package com.example.calorie_tracking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "meal_entry_meals")
@IdClass(MealEntryMealId.class) // Используем составной ключ
public class MealEntryMeal {

    // Удаляем автоинкрементный ID
    @Id
    @ManyToOne
    @JoinColumn(name = "meal_entry_id", nullable = false)
    private MealEntry mealEntry;

    @Id
    @ManyToOne
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @Column(nullable = false)
    @Min(value = 1, message = "Количество порций должно быть не менее 1")
    private int quantity;
}
//@Getter
//@Setter
//@Entity
//@Table(name = "meal_entry_meals")
//public class MealEntryMeal {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "meal_entry_id")
//    private MealEntry mealEntry;
//
//    @ManyToOne
//    @JoinColumn(name = "meal_id")
//    private Meal meal;
//
//    private int quantity; // количество порций
//}
