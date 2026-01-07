package com.example.demo.service.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MON(1, "пн"),
    TUE(2, "вт"),
    WED(3, "ср"),
    THU(4, "чт"),
    FRI(5, "пт");

    private final int number;
    private final String name;
}
