package com.example.demo.service.repositoryservice;

import com.example.demo.dao.StudentGroupRepository;
import com.example.demo.dao.entities.StudentGroupModel;
import com.example.demo.service.converter.modeltodto.StudentGroupModelToStudentGroupDTOConverter;
import com.example.demo.service.dto.StudentGroupDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentGroupService {
    private final StudentGroupRepository repository;
    private final StudentGroupModelToStudentGroupDTOConverter converter;

    public StudentGroupDTO getOrCreate(String name) {
        Optional<StudentGroupDTO> studentGroup = this.getStudentGroupByName(name);
        if (studentGroup.isEmpty()) {
            StudentGroupModel studentGroupModel = new StudentGroupModel();
            studentGroupModel.setName(name);
            return converter.convert(repository.save(studentGroupModel));
        } else {
            return studentGroup.get();
        }
    }

    public List<String> getGroups() {
        return repository.getGroupsName();
    }

    public StudentGroupDTO getStudentGroup(int id) {
        return converter.convert(repository.findById(id).get()); // add a check on present
    }

    public Optional<StudentGroupDTO> getStudentGroupByName(String name) {
        return repository.findByName(name).map(converter::convert);
    }
}
