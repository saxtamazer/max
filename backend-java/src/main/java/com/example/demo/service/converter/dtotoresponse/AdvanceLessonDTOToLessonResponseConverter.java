package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LessonResponse;
import com.example.demo.service.dto.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@Service
public class AdvanceLessonDTOToLessonResponseConverter implements Converter<AdvancedLessonDTO, LessonResponse> {
    @Override
    public LessonResponse convert(AdvancedLessonDTO source) {
        LessonResponse response = new LessonResponse();

        LocalDateTime now = LocalDateTime.now();

        response.setId(source.getId());
        response.setGroup(source.getGroup().getName());
        response.setSubject(formatSubject(source.getSubject()));
        response.setTeacher(
                Arrays.stream(source.getEducators())
                        .map(EducatorDTO::getFullName)
                        .toArray(String[]::new)
        );
        response.setRoom(
                Arrays.stream(source.getAuditoriums())
                        .map(this::formatAuditorium)
                        .toArray(String[]::new)
        );
        response.setStartTime(handleTimeslot(now, source.getTimeslot(), source.getTimeslot().getStartTime()));
        response.setEndTime(handleTimeslot(now, source.getTimeslot(), source.getTimeslot().getEndTime()));
        response.setWeekType(source.getTimeslot().isEven() ? "EVEN" : "ODD");
        return response;
    }

    private String formatSubject(SubjectDTO subject) { // add return format like "пр. Математика"
        return subject.getName();
    }

    private String formatAuditorium(AuditoriumDTO auditorium) {
        return auditorium.getBlock() + "-" + auditorium.getIdent();
    }

    private LocalDateTime handleTimeslot(LocalDateTime referenceDay, TimeslotDTO timeslot, LocalTime time) {
        return referenceDay
                .with(DayOfWeek.of(timeslot.getDayOfWeek()))
                .withHour(time.getHour())
                .withMinute(time.getMinute());
    }
}
