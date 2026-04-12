package com.example.demo.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class LoadEducatorResponse {
    boolean even;

    @JsonProperty("day_of_week")
    int dayOfWeek;

    @JsonProperty("pair_number")
    int pairNumber;

    @JsonProperty("pair_type")
    String pairType;

    @JsonProperty("pair_name")
    String pairName;

    List<String> groups;

    List<String> auditoriums;
}
