CREATE TABLE meals (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    calories_per_serving DOUBLE PRECISION NOT NULL CHECK (calories_per_serving > 0),
    proteins DOUBLE PRECISION,
    fats DOUBLE PRECISION,
    carbohydrates DOUBLE PRECISION
);