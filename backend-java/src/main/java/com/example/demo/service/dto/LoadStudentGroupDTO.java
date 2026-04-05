package com.example.demo.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadStudentGroupDTO {
    int timeslotId;
    boolean even;
    int dayOfWeek;
    int pairNumber;
    String pairName;
    String pairType;
    String auditoriums;
}
