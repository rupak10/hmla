package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user_exercise")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_minutes", nullable = false)
    private Integer totalMinutes;

    @Column(name = "calories_burnt", nullable = false)
    private Integer caloriesBurnt;

    @Column(name = "exercise_name", nullable = false)
    private String exerciseName;

    @Column(name = "intake_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date exerciseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
