package com.app.repository;

import com.app.model.Exercise;
import com.app.model.Sleep;
import com.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface SleepRepository extends JpaRepository<Sleep, Long> {
    List<Sleep> findByUser(User user);
    Optional<Sleep> findBySleepDate(Date sleepDate);
    Optional<Sleep> findBySleepDateAndUser(Date sleepDate, User user);
}