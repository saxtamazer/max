package com.example.demo.utils;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor()
public enum CustomDayOfWeek {
    MONDAY ("ПОНЕДЕЛЬНИК"),
    TUESDAY ("ВТОРНИК"),
    WEDNESDAY ("СРЕДА"),
    THURSDAY ("ЧЕТВЕРГ"),
    FRIDAY ("ПЯТНИЦА"),
    SATURDAY ("СУББОТА"),
    SUNDAY ("ВОСКРЕСЕНЬЕ");

    String rusName;

    public int getOrder() {
        return this.ordinal() + 1;
    }
}
