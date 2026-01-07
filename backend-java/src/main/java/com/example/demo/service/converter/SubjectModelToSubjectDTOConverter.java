package com.example.demo.service.converter;

import com.example.demo.dao.entities.SubjectModel;
import com.example.demo.service.dto.SubjectDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class SubjectModelToSubjectDTOConverter implements Converter<SubjectModel, SubjectDTO> {
    @Override
    public SubjectDTO convert(SubjectModel source) {
        return new SubjectDTO(source.getName(), source.getTypeId());
    }
}
