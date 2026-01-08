package com.example.demo.service.converter.dtotoresponse;

import com.example.demo.api.json.LessonResponse;
import com.example.demo.service.dto.AdvancedLessonDTO;
import com.example.demo.service.dto.AuditoriumDTO;
import com.example.demo.service.dto.EducatorDTO;
import com.example.demo.service.dto.SubjectDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class AdvanceLessonDTOToLessonResponseConverter implements Converter<AdvancedLessonDTO, LessonResponse> {
    @Override
    public LessonResponse convert(AdvancedLessonDTO source) {
        LessonResponse response = new LessonResponse();

        response.setId(source.getId());
        response.setGroup(source.getGroup().getName());
        response.setSubject(handleSubject(source.getSubject()));
        response.setTeacher(handleEducatorFullName(source.getEducator()));
        response.setRoom(handleAuditorium(source.getAuditorium()));
        response.setStartTime(source.getTimeslot().getStartTime().toString());
        response.setEndTime(source.getTimeslot().getEndTime().toString());
        response.setWeekType(source.getTimeslot().isEven() ? "EVEN" : "ODD");
        return response;
    }

    private String handleSubject(SubjectDTO subject) { // add return format like "пр. Математика"
        return subject.getName();
    }

    private String handleEducatorFullName(EducatorDTO educator) {
        return educator.getLastName() + " "
                + educator.getFirstName().charAt(0) + ". "
                /*+ educator.getMiddleName().charAt(0) + "."*/; // add check for null middle name
    }

    private String handleAuditorium(AuditoriumDTO auditorium) {
        return auditorium.getBlock() + "-" + auditorium.getNumber();
    }
}
