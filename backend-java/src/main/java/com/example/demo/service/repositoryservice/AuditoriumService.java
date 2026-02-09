package com.example.demo.service.repositoryservice;

import com.example.demo.dao.AuditoriumRepository;
import com.example.demo.dao.entities.AuditoriumModel;
import com.example.demo.service.converter.modeltodto.AuditoriumModelToAuditoriumDTOConverter;
import com.example.demo.service.dto.AuditoriumDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditoriumService {
    private final AuditoriumRepository repository;
    private final AuditoriumModelToAuditoriumDTOConverter converter;

    public AuditoriumDTO getOrCreate(String block, String ident) {
        Optional<AuditoriumDTO> auditorium = this.getAuditoriumByBlockAndIdent(block, ident);
        if (auditorium.isEmpty()) {
            AuditoriumModel auditoriumModel = new AuditoriumModel();
            auditoriumModel.setBlock(block);
            auditoriumModel.setIdent(ident);
            return converter.convert(repository.save(auditoriumModel));
        } else {
            return auditorium.get();
        }
    }

    public Optional<AuditoriumDTO> getAuditoriumById(int id) {
        return repository.findById(id).map(converter::convert);
    }

    public List<AuditoriumDTO> getAllAuditoriumByIds(List<Integer> ids) {
        return repository.findAllById(ids).stream()
                .map(converter::convert)
                .toList();
    }

    public Optional<AuditoriumDTO> getAuditoriumByBlockAndIdent(String block, String ident) {
        return repository.getAuditoriumModelByBlockAndIdent(block, ident).map(converter::convert);
    }
}
