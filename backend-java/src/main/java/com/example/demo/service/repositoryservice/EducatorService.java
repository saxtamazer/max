package com.example.demo.service.repositoryservice;

import com.example.demo.dao.EducatorRepository;
import com.example.demo.dao.entities.EducatorModel;
import com.example.demo.service.converter.modeltodto.EducatorModelToEducatorDTOConverter;
import com.example.demo.service.dto.EducatorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducatorService {
    private final EducatorRepository repository;
    private final EducatorModelToEducatorDTOConverter converter;

    public EducatorDTO getOrCreateByFullName(String fullName) {
        Optional<EducatorDTO> educator = getEducatorByFullName(fullName);
        if (educator.isEmpty()) {
            EducatorModel educatorModel = new EducatorModel();
            educatorModel.setFullName(fullName);
            return converter.convert(repository.save(educatorModel));
        } else {
            return educator.get();
        }
    }

    public Optional<EducatorDTO> getEducatorById(int id) {
        return repository.findById(id).map(converter::convert);
    }

    public Optional<EducatorDTO> getEducatorByFullName(String fullName) {
        return repository.findByFullName(fullName).map(converter::convert);
    }
}
