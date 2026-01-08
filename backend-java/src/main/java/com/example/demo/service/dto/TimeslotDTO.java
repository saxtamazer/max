package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class TimeslotDTO {
    private boolean even;
    private int dayOfWeek;
    private int pairNumber;
    private LocalTime startTime;
    private LocalTime endTime;
}
