package com.app.repository;

import com.app.model.Calorie;
import com.app.model.Exercise;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByUser(User user);
    Optional<Exercise> findByExerciseDate(Date exerciseDate);
    Optional<Exercise> findByExerciseDateAndUser(Date exerciseDate, User user);
}