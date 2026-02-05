package com.example.demo.service.tools;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@RequiredArgsConstructor
public enum TypeLesson {
    PRACTICE ("пр"),
    LECTURE ("лек"),
    LABORATORY ("лаб");

    String rusType;
}
