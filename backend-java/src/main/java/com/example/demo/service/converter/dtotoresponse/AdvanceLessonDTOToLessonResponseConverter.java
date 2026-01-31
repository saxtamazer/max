package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LessonResponse;
import com.example.demo.service.dto.*;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        response.setTeacher(formatEducatorFullName(source.getEducator()));
        response.setRoom(formatAuditorium(source.getAuditorium()));
        response.setStartTime(handleTimeslot(now, source.getTimeslot(), source.getTimeslot().getStartTime()));
        response.setEndTime(handleTimeslot(now, source.getTimeslot(), source.getTimeslot().getEndTime()));
        response.setWeekType(source.getTimeslot().isEven() ? "EVEN" : "ODD");
        return response;
    }

    private String formatSubject(SubjectDTO subject) { // add return format like "пр. Математика"
        return subject.getName();
    }

    private String formatEducatorFullName(EducatorDTO educator) {
        return String.format("%s %s %s",
                educator.getLastName(),
                educator.getFirstName().charAt(0) + ".",
                Optional.ofNullable(educator.getMiddleName())
                        .filter(name -> !name.isEmpty())
                        .map(name -> name.charAt(0) + ".")
                        .orElse("")
        ).trim();
    }

    private String formatAuditorium(AuditoriumDTO auditorium) {
        return auditorium.getBlock() + "-" + auditorium.getNumber();
    }

    private LocalDateTime handleTimeslot(LocalDateTime referenceDay, TimeslotDTO timeslot, LocalTime time) {
        return referenceDay
                .with(DayOfWeek.of(timeslot.getDayOfWeek()))
                .withHour(time.getHour())
                .withMinute(time.getMinute());
    }
}
