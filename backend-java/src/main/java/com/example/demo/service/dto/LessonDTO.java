package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LessonDTO {
    private int id;
    private int groupId;
    private int subjectId;
    private int educatorId;
    private int auditoriumId;
    private int timeslotId;
}
