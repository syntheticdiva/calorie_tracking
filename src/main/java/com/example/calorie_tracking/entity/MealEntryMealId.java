package com.example.calorie_tracking.entity;

import java.io.Serializable;
import java.util.Objects;

public class MealEntryMealId implements Serializable {
    private Long mealEntry;
    private Long meal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealEntryMealId that = (MealEntryMealId) o;
        return Objects.equals(mealEntry, that.mealEntry) && Objects.equals(meal, that.meal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealEntry, meal);
    }
}
