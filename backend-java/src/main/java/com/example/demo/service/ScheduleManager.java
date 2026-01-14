package com.example.demo.service;

import com.example.demo.api.json.ScheduleResponse;
import com.example.demo.dao.lesson.EvenFilter;
import com.example.demo.service.converter.dtotoresponse.AdvanceLessonDTOToLessonResponseConverter;
import com.example.demo.service.dto.AdvancedLessonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleManager {
    private final LessonService lessonService;
    private final AdvanceLessonDTOToLessonResponseConverter converter;

    public ScheduleResponse getScheduleByThisWeek() {
        EvenFilter evenFilter = getEvenFilter();
        List<AdvancedLessonDTO> lessons = lessonService.getAllAdvancedLessonsByEven(evenFilter);
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setEvents(lessons.stream().map(converter::convert).toList());
        scheduleResponse.setCurrentWeekIsEven(evenFilter.isEven());
        return scheduleResponse;
    }

    private EvenFilter getEvenFilter() {
        Calendar c = Calendar.getInstance();
        return new EvenFilter(c.get(Calendar.WEEK_OF_MONTH) % 2 == 0);
    }
}
