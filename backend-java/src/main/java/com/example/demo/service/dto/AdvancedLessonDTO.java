package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvancedLessonDTO {
    private StudentGroupDTO group;
    private SubjectDTO subject;
    private EducatorDTO educator;
    private AuditoriumDTO auditorium;
    private TimeslotDTO timeslot;
}
