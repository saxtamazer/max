package com.example.demo.service.converter;

import com.example.demo.dao.entities.StudentGroupModel;
import com.example.demo.service.dto.StudentGroupDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class StudentGroupModelToStudentGroupDTOConverter implements Converter<StudentGroupModel, StudentGroupDTO> {
    @Override
    public StudentGroupDTO convert(StudentGroupModel source) {
        return new StudentGroupDTO(source.getName());
    }
}
