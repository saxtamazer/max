package com.example.demo.service;

import com.example.demo.api.json.ScheduleResponse;
import com.example.demo.dao.lesson.EvenFilter;
import com.example.demo.service.converter.dtotoresponse.AdvanceLessonDTOToLessonResponseConverter;
import com.example.demo.service.dto.AdvancedLessonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleManager {
    private final LessonService lessonService;
    private final AdvanceLessonDTOToLessonResponseConverter converter;

    public ScheduleResponse getScheduleByThisWeek() {
        List<AdvancedLessonDTO> lessons = lessonService.getAllAdvancedLessonsByEven(getEvenFilter());
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setEvents(lessons.stream().map(converter::convert).toList());
        scheduleResponse.setCurrentWeekIsEven(false); // todo handle by even week
        return scheduleResponse;
    }

    private EvenFilter getEvenFilter() {
        return new EvenFilter(false); // todo handle by even week
    }
}
