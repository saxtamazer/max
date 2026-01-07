package com.example.demo.service.converter;

import com.example.demo.dao.entities.LessonModel;
import com.example.demo.service.dto.LessonDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class LessonModelToLessonDTOConverter implements Converter<LessonModel, LessonDTO> {
    @Override
    public LessonDTO convert(LessonModel source) {
        return new LessonDTO(
                source.getGroupId(),
                source.getGroupId(),
                source.getEducatorId(),
                source.getAuditoriumId(),
                source.getTimeslotId()
        );
    }
}
