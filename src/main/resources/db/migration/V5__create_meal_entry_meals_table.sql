CREATE TABLE meal_entry_meals (
    meal_entry_id BIGINT NOT NULL,
    meal_id BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    PRIMARY KEY (meal_entry_id, meal_id),
    FOREIGN KEY (meal_entry_id) REFERENCES meal_entries(id),
    FOREIGN KEY (meal_id) REFERENCES meals(id)
);