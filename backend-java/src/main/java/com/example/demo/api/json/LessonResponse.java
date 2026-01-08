package com.example.demo.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonResponse {
    private int id;
    private String group;
    private String subject;
    private String teacher;
    private String room;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("end_time")
    private String endTime;

    @JsonProperty("week_type")
    private String weekType;
}
