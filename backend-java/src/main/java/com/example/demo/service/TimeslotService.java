package com.example.demo.service;

import com.example.demo.dao.TimeslotRepository;
import com.example.demo.service.converter.TimeslotModelToTimeslotDTOConverter;
import com.example.demo.service.dto.TimeslotDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeslotService {
    private final TimeslotRepository repository;
    private final TimeslotModelToTimeslotDTOConverter converter;

    public TimeslotDTO getTimeslotById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }
}
