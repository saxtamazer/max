package com.example.demo.service.repositoryservice;

import com.example.demo.dao.SubjectRepository;
import com.example.demo.dao.entities.SubjectModel;
import com.example.demo.service.converter.modeltodto.SubjectModelToSubjectDTOConverter;
import com.example.demo.service.dto.SubjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository repository;
    private final SubjectModelToSubjectDTOConverter converter;

    public SubjectDTO getOrCreate(String name, int typeId) {
        Optional<SubjectDTO> subject = this.getSubjectByNameAndTypeId(name, typeId);
        if (subject.isEmpty()) {
            SubjectModel subjectModel = new SubjectModel();
            subjectModel.setName(name);
            subjectModel.setTypeId(typeId);
            return converter.convert(repository.save(subjectModel));
        } else {
            return subject.get();
        }
    }

    public SubjectDTO getSubjectById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }

    public Optional<SubjectDTO> getSubjectByNameAndTypeId(String name, int typeId) {
        return repository.findByNameAndTypeId(name, typeId).map(converter::convert);
    }
}
