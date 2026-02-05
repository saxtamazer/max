package com.example.demo.service;

import com.example.demo.api.json.LessonRequest;
import com.example.demo.api.json.ScheduleResponse;
import com.example.demo.configuration.ConfigProperties;
import com.example.demo.dao.lesson.EvenFilter;
import com.example.demo.service.converter.dtotoresponse.AdvanceLessonDTOToLessonResponseConverter;
import com.example.demo.service.dto.AdvancedLessonDTO;
import com.example.demo.service.dto.StudentGroupDTO;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleManager {
    ConfigProperties configProperties;
    LessonService lessonService;
    StudentGroupService studentGroupService;
    TimeslotService timeslotService;
    AdvanceLessonDTOToLessonResponseConverter converter;

    public ScheduleResponse getScheduleByThisWeek() {
        EvenFilter evenFilter = getEvenFilter();
        List<AdvancedLessonDTO> lessons = lessonService.getAllAdvancedLessonsByEven(evenFilter);
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setEvents(lessons.stream().map(converter::convert).toList());
        scheduleResponse.setCurrentWeekIsEven(evenFilter.isEven());
        return scheduleResponse;
    }

    public void writeSchedule() {
        List<LessonRequest> schedule = extractSchedule();

    }

    private LocalTime handleStartTime(String jsonStartTime) {
        String[] splitTime = jsonStartTime.split("\\.");
        return LocalTime.of(Integer.parseInt(splitTime[0]), Integer.parseInt(splitTime[1]));
    }

    private List<LessonRequest> extractSchedule() {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(configProperties.getScheduleStorage() + "schedule.json");
        List<LessonRequest> schedule = objectMapper.readValue(jsonFile, new TypeReference<List<LessonRequest>>() {});
        return schedule;
    }

    private EvenFilter getEvenFilter() {
        Calendar c = Calendar.getInstance();
        return new EvenFilter(c.get(Calendar.WEEK_OF_MONTH) % 2 == 0);
    }
}
