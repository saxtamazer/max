package com.example.demo.service.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadEducatorDTO {
    boolean even;
    int dayOfWeek;
    int pairNumber;
    String pairType;
    String pairName;
    String groups;
    String auditoriums;
}
