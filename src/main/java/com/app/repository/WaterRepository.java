package com.app.repository;

import com.app.model.User;
import com.app.model.Water;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface WaterRepository extends JpaRepository<Water, Long> {
    List<Water> findByUser(User user);
    Optional<Water> findByIntakeDate(Date intakeDate);
    Optional<Water> findByIntakeDateAndUser(Date intakeDate, User user);
}