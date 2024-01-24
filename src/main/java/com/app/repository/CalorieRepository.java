package com.app.repository;

import com.app.model.Calorie;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CalorieRepository extends JpaRepository<Calorie, Long> {
    List<Calorie> findByUser(User user);
    Optional<Calorie> findByIntakeDate(Date intakeDate);
    Optional<Calorie> findByIntakeDateAndUser(Date intakeDate, User user);
}