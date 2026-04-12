package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LessonResponse;
import com.example.demo.service.converter.TimeslotLocalTimeToLocalDateTimeConverter;
import com.example.demo.service.dto.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AdvanceLessonDTOToLessonResponseConverter implements Converter<AdvancedLessonDTO, LessonResponse> {
    TimeslotLocalTimeToLocalDateTimeConverter timeConverter;

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
        response.setStartTime(timeConverter.convertWithDayOfWeek(
                source.getTimeslot().getDayOfWeek(), source.getTimeslot().getStartTime())
        );
        response.setEndTime(timeConverter.convertWithDayOfWeek(
                source.getTimeslot().getDayOfWeek(), source.getTimeslot().getEndTime())
        );
        response.setWeekType(source.getTimeslot().isEven() ? "EVEN" : "ODD");
        return response;
    }

    private String formatSubject(SubjectDTO subject) { // add return format like "пр. Математика"
        return subject.getName();
    }

    private String formatAuditorium(AuditoriumDTO auditorium) {
        return auditorium.getBlock() + "-" + auditorium.getIdent();
    }
}
