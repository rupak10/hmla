package com.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "user_calories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Calorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "food_type", nullable = false)
    private String foodType;

    @Column(name = "calories", nullable = false)
    private double calories;

    @Column(name = "amount_of_food", nullable = false)
    private double amountOfFood;

    @Column(name = "intake_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date intakeDate;

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
