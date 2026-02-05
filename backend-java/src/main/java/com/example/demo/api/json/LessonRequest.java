package com.example.demo.api.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Arrays;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonRequest {
    String group;

    @JsonProperty("day_of_week")
    String dayOfWeek;

    @JsonProperty("start_time")
    String startTime;

    @JsonProperty("end_time")
    String endTime;

    String type;
    String subject;
    String[] teachers;
    String[] rooms;
    boolean even;

    @Override
    public String toString() {
        return "LessonRequest{" +
                "group='" + group + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", type='" + type + '\'' +
                ", subject='" + subject + '\'' +
                ", teachers=" + Arrays.toString(teachers) +
                ", rooms=" + Arrays.toString(rooms) +
                ", even=" + even +
                '}';
    }
}
