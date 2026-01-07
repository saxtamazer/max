package com.example.demo.service;

import com.example.demo.dao.SubjectRepository;
import com.example.demo.service.converter.SubjectModelToSubjectDTOConverter;
import com.example.demo.service.dto.SubjectDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository repository;
    private final SubjectModelToSubjectDTOConverter converter;

    public SubjectDTO getSubjectById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }
}
