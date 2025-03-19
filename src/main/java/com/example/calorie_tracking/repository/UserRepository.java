package com.example.calorie_tracking.repository;

import com.example.calorie_tracking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
