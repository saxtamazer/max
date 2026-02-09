package com.example.demo.service;

import com.example.demo.api.json.LessonRequest;
import com.example.demo.api.json.ScheduleResponse;
import com.example.demo.configuration.ConfigProperties;
import com.example.demo.utils.filter.EvenFilter;
import com.example.demo.utils.filter.GroupFilter;
import com.example.demo.service.repositoryservice.LessonService;
import com.example.demo.service.converter.dtotoresponse.AdvanceLessonDTOToLessonResponseConverter;
import com.example.demo.service.dto.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ScheduleManager {
    ConfigProperties configProperties;
    LessonService lessonService;
    LessonApplicationService lessonApplicationService;
    AdvanceLessonDTOToLessonResponseConverter converter;

    public ScheduleResponse getScheduleByThisWeek() {
        EvenFilter evenFilter = new EvenFilter(isEvenWeek());
        List<AdvancedLessonDTO> lessons = lessonService.getAllAdvancedLessonsByFilter(evenFilter);
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setEvents(lessons.stream().map(converter::convert).toList());
        scheduleResponse.setCurrentWeekIsEven(evenFilter.isEven());
        return scheduleResponse;
    }

    public ScheduleResponse getScheduleByGroup(String groupName) {
        GroupFilter groupFilter = new GroupFilter(groupName);
        List<AdvancedLessonDTO> lessons = lessonService.getAllAdvancedLessonsByFilter(groupFilter);
        ScheduleResponse scheduleResponse = new ScheduleResponse();
        scheduleResponse.setEvents(lessons.stream().map(converter::convert).toList());
        scheduleResponse.setCurrentWeekIsEven(isEvenWeek());
        return scheduleResponse;
    }

    public void writeSchedule() {
        List<LessonRequest> schedule = extractSchedule();
        for (LessonRequest lessonRequest : schedule) {
            lessonApplicationService.save(lessonRequest);
        }
    }

    private List<LessonRequest> extractSchedule() {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(configProperties.getScheduleStorage() + "schedule.json");
        List<LessonRequest> schedule = objectMapper.readValue(
                jsonFile, new TypeReference<List<LessonRequest>>() {}
        );
        return schedule;
    }

    private boolean isEvenWeek() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.WEEK_OF_MONTH) % 2 == 0;
    }
}
