package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SleepDTO {
    private String sl;

    private String totalSleepTime;
    private String sleepDate;

    private Long id;
}
