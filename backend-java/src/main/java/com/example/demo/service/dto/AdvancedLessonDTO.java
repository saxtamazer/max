package com.example.demo.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdvancedLessonDTO {
    private int id;
    private StudentGroupDTO group;
    private SubjectDTO subject;
    private EducatorDTO[] educators;
    private AuditoriumDTO[] auditoriums;
    private TimeslotDTO timeslot;
}
