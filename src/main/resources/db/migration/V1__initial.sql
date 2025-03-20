CREATE TYPE gender_enum AS ENUM ('MALE', 'FEMALE');
CREATE TYPE goal_enum AS ENUM ('WEIGHT_LOSS', 'MAINTENANCE', 'MUSCLE_GAIN');

--ALTER TABLE users
--    ALTER COLUMN gender TYPE gender_enum USING gender::gender_enum,
--    ALTER COLUMN goal TYPE goal_enum USING goal::goal_enum;