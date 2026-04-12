package com.example.demo.service.converter.modeltodto;

import com.example.demo.dao.entities.SubjectModel;
import com.example.demo.service.dto.SubjectDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class SubjectModelToSubjectDTOConverter implements Converter<SubjectModel, SubjectDTO> {
    @Override
    public SubjectDTO convert(SubjectModel source) {
        return source == null ?
                null
                :
                new SubjectDTO(
                        source.getId(), source.getName(), source.getTypeId()
                );
    }
}
