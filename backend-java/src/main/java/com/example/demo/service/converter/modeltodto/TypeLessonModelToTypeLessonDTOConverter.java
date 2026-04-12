package com.example.demo.service.converter.modeltodto;

import com.example.demo.dao.entities.TypeLessonModel;
import com.example.demo.service.dto.TypeLessonDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TypeLessonModelToTypeLessonDTOConverter implements Converter<TypeLessonModel, TypeLessonDTO> {
    @Override
    public TypeLessonDTO convert(TypeLessonModel source) {
        return source == null ?
                null
                :
                new TypeLessonDTO(
                        source.getId(),
                        source.getName()
                );
    }
}
