package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user_daily_goals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calories", nullable = false)
    private Double calories;

    @Column(name = "exercise_minutes", nullable = false)
    private Integer exerciseMinutes;

    @Column(name = "sleep", nullable = false)
    private Double sleep;

    @Column(name = "water", nullable = false)
    private Double water;

    @Column(name = "date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

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
