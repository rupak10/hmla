package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CalorieDTO {
    private String sl;
    private String foodType;
    private String calories;
    private String amountOfFood;
    private String intakeDate;

    private Long id;
}
