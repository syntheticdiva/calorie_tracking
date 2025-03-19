CREATE TABLE meal_entry_meals (
    meal_entry_id BIGINT NOT NULL,
    meal_id BIGINT NOT NULL,
    PRIMARY KEY (meal_entry_id, meal_id),
    FOREIGN KEY (meal_entry_id) REFERENCES meal_entries(id) ON DELETE CASCADE,
    FOREIGN KEY (meal_id) REFERENCES meals(id) ON DELETE CASCADE
);