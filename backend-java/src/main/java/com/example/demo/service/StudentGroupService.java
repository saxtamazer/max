package com.example.demo.service;

import com.example.demo.dao.StudentGroupRepository;
import com.example.demo.service.converter.modeltodto.StudentGroupModelToStudentGroupDTOConverter;
import com.example.demo.service.dto.StudentGroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentGroupService {
    private final StudentGroupRepository repository;
    private final StudentGroupModelToStudentGroupDTOConverter converter;

    public StudentGroupDTO getStudentGroup(int id) {
        return converter.convert(repository.findById(id).get()); // add a check on present
    }
}
