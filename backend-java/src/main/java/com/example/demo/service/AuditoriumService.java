package com.example.demo.service;

import com.example.demo.dao.AuditoriumRepository;
import com.example.demo.service.converter.modeltodto.AuditoriumModelToAuditoriumDTOConverter;
import com.example.demo.service.dto.AuditoriumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditoriumService {
    private final AuditoriumRepository repository;
    private final AuditoriumModelToAuditoriumDTOConverter converter;

    public AuditoriumDTO getAuditoriumById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }
}
