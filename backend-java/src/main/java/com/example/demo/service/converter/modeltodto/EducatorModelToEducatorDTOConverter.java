package com.example.demo.service.converter.modeltodto;

import com.example.demo.dao.entities.EducatorModel;
import com.example.demo.service.dto.EducatorDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class EducatorModelToEducatorDTOConverter implements Converter<EducatorModel, EducatorDTO> {
    @Override
    public EducatorDTO convert(EducatorModel source) {
        return source == null ?
                null
                :
                new EducatorDTO(
                        source.getId(),
                        source.getFullName()
                );
    }
}
