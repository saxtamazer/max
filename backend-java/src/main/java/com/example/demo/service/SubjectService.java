package com.example.demo.service;

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

    public Optional<SubjectDTO> create(String name, int typeId) {
        if (!repository.existsById(typeId)) {
            SubjectModel subject = new SubjectModel();
            subject.setName(name);
            subject.setTypeId(typeId);
            return Optional.of(converter.convert(repository.save(subject)));
        } else {
            return Optional.empty();
        }
    }

    public SubjectDTO getSubjectById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }

    public Optional<SubjectDTO> findSubjectByName(String name) {
        return repository.findByName(name).map(converter::convert);
    }
}
