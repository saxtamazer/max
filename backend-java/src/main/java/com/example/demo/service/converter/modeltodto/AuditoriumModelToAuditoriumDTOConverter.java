package com.example.demo.service.converter;

import com.example.demo.dao.entities.AuditoriumModel;
import com.example.demo.service.dto.AuditoriumDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class AuditoriumModelToAuditoriumDTOConverter implements Converter<AuditoriumModel, AuditoriumDTO> {
    @Override
    public AuditoriumDTO convert(AuditoriumModel source) {
        return new AuditoriumDTO(source.getBlock(), source.getNumber());
    }
}
