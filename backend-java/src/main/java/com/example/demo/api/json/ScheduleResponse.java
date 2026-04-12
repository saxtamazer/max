package com.example.demo.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ScheduleResponse {
    private List<LessonResponse> events;

    @JsonProperty("current_week_is_even")
    private boolean currentWeekIsEven;
}
