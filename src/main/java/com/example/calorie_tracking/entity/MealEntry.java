package com.example.calorie_tracking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "meal_entries")

public class MealEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate date;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "meal_entry_meals",
            joinColumns = @JoinColumn(name = "meal_entry_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;
    @OneToMany(mappedBy = "mealEntry", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealEntryMeal> mealEntries = new ArrayList<>();

    public double getTotalCalories() {
        return mealEntries.stream()
                .mapToDouble(me -> me.getMeal().getCaloriesPerServing() * me.getQuantity())
                .sum();
    }
}