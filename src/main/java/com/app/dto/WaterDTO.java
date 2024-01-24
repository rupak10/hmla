package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaterDTO {
    private String sl;

    private String amount;
    private String intakeDate;

    private Long id;
}
