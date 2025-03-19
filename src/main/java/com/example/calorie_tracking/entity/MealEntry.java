package com.example.calorie_tracking.entity;

import com.example.calorie_tracking.dto.UserDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    public double getTotalCalories() {
        return meals != null ?
                meals.stream().mapToDouble(Meal::getCaloriesPerServing).sum()
                : 0.0;
    }

}