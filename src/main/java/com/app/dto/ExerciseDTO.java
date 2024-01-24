package com.app.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseDTO {
    private String sl;

    private String totalMinutes;
    private String caloriesBurnt;
    private String exerciseName;
    private String exerciseDate;

    private Long id;
}
