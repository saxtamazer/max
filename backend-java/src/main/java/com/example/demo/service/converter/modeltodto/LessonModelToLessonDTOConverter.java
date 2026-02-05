package com.example.demo.service.converter.modeltodto;

import com.example.demo.dao.entities.AuditoriumModel;
import com.example.demo.dao.entities.EducatorModel;
import com.example.demo.dao.entities.LessonModel;
import com.example.demo.service.dto.LessonDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class LessonModelToLessonDTOConverter implements Converter<LessonModel, LessonDTO> {
    @Override
    public LessonDTO convert(LessonModel source) {
        return source == null ?
                null
                :
                new LessonDTO(
                        source.getId(),
                        source.getGroupId(),
                        source.getGroupId(),
                        source.getEducators()
                                .stream()
                                .mapToInt(EducatorModel::getId)
                                .toArray(),
                        source.getAuditoriums()
                                .stream()
                                .mapToInt(AuditoriumModel::getId)
                                .toArray(),
                        source.getTimeslotId()
                );
    }
}
