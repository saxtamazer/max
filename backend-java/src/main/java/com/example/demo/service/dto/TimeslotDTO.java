package com.example.demo.service.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TimeslotDTO {
    int id;
    boolean even;
    int dayOfWeek;
    int pairNumber;
    LocalTime startTime;
    LocalTime endTime;
}
