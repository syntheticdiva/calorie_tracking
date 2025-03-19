CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    age INT NOT NULL CHECK (age > 0),
    gender VARCHAR(50) NOT NULL,
    weight DOUBLE PRECISION NOT NULL CHECK (weight > 0),
    height DOUBLE PRECISION NOT NULL CHECK (height > 0),
    goal VARCHAR(50) NOT NULL,
    daily_calorie_intake DOUBLE PRECISION
);