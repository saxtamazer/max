package com.example.demo.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadAuditroiumDTO {
    int timeslotId;
    boolean isEven;
    int dayOfWeek;
    int pairNumber;
    LocalTime startTime;
    LocalTime endTime;
    String groups;
}
