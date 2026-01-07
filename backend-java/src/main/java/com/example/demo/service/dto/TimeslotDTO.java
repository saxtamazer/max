package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeslotDTO {
    private boolean even;
    private int day_of_week;
    private int pair_number;
    private LocalTime start_time;
    private LocalTime end_time;
}
