package com.example.demo.service;

import com.example.demo.service.dto.AdvancedLessonDTO;
import com.example.demo.service.dto.LessonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleManager {
    private final LessonService lessonService;

    public List<AdvancedLessonDTO> getFullSchedule() {
        List<LessonDTO> lessons = lessonService.getAllLessons();
        return null;
    }
}
