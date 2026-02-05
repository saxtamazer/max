package com.example.demo.service;

import com.example.demo.dao.StudentGroupRepository;
import com.example.demo.dao.entities.StudentGroupModel;
import com.example.demo.service.converter.modeltodto.StudentGroupModelToStudentGroupDTOConverter;
import com.example.demo.service.dto.StudentGroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentGroupService {
    private final StudentGroupRepository repository;
    private final StudentGroupModelToStudentGroupDTOConverter converter;

    public Optional<StudentGroupDTO> create(String name) {
        if (!repository.existsByName(name)) {
            StudentGroupModel studentGroupModel = new StudentGroupModel();
            studentGroupModel.setName(name);
            return Optional.of(converter.convert(repository.save(studentGroupModel)));
        } else {
            return Optional.empty();
        }
    }

    public StudentGroupDTO getStudentGroup(int id) {
        return converter.convert(repository.findById(id).get()); // add a check on present
    }

    public Optional<StudentGroupDTO> getStudentGroupByName(String name) {
        return repository.findByName(name).map(converter::convert);
    }
}
