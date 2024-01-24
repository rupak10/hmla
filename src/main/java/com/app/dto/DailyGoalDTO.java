package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyGoalDTO {
    private String sl;

    private String calories;
    private String exerciseMinutes;
    private String sleep;
    private String water;
    private String date;

    private Long id;
}
