package com.app.repository;

import com.app.model.DailyGoal;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DailyGoalRepository extends JpaRepository<DailyGoal, Long> {
    List<DailyGoal> findByUser(User user);
    Optional<DailyGoal> findByDate(Date date);
    Optional<DailyGoal> findByDateAndUser(Date date, User user);
}