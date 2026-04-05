package com.example.demo.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadAuditoriumResponse {
    @JsonProperty("is_even")
    boolean even;

    @JsonProperty("day_of_week")
    int dayOfWeek;

    @JsonProperty("pair_number")
    int pairNumber;

    List<String> groups;
}
