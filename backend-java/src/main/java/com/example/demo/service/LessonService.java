package com.example.demo.service;

import com.example.demo.dao.lesson.LessonRepository;
import com.example.demo.service.converter.LessonModelToLessonDTOConverter;
import com.example.demo.service.dto.AdvancedLessonDTO;
import com.example.demo.service.dto.LessonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final LessonModelToLessonDTOConverter converter;
    private final StudentGroupService studentGroupService;
    private final SubjectService subjectService;
    private final EducatorService educatorService;
    private final AuditoriumService auditoriumService;
    private final TimeslotService timeslotService;

    public List<LessonDTO> getAllLessons() {
        return lessonRepository
                .findAll()
                .stream()
                .map(converter::convert)
                .toList();
    }

    public List<AdvancedLessonDTO> getAllAdvancedLessons() {
        return this.getAllLessons()
                .stream()
                .map(lesson -> {
                    return new AdvancedLessonDTO(
                        studentGroupService.getStudentGroup(lesson.getGroupId()),
                        subjectService.getSubjectById(lesson.getSubjectId()),
                        educatorService.getEducatorById(lesson.getEducatorId()),
                        auditoriumService.getAuditoriumById(lesson.getAuditoriumId()),
                        timeslotService.getTimeslotById(lesson.getTimeslotId())
                    );
                })
                .toList();
    }
}
