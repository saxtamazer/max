package com.example.demo.service;

import com.example.demo.dao.EducatorRepository;
import com.example.demo.service.converter.EducatorModelToEducatorDTOConverter;
import com.example.demo.service.dto.EducatorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducatorService {
    private final EducatorRepository repository;
    private final EducatorModelToEducatorDTOConverter converter;

    public EducatorDTO getEducatorById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }
}
