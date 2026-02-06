package com.example.demo.service.repositoryservice;

import com.example.demo.dao.TimeslotRepository;
import com.example.demo.service.converter.modeltodto.TimeslotModelToTimeslotDTOConverter;
import com.example.demo.service.dto.TimeslotDTO;
import com.example.demo.service.tools.CustomDayOfWeek;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimeslotService {
    private final TimeslotRepository repository;
    private final TimeslotModelToTimeslotDTOConverter converter;

    public TimeslotDTO getTimeslotById(int id) {
        return converter.convert(repository.findById(id).get());  // add a check on present
    }

    public Optional<TimeslotDTO> getTimeslotByDayAndStartTimeAndEven(String dayOfWeek, LocalTime startTime, boolean isEven) {
        CustomDayOfWeek day = Arrays.stream(CustomDayOfWeek.values())
                .filter(d -> d.getRusName().equals(dayOfWeek))
                .findFirst()
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid day of week: " + dayOfWeek)
                );
        return isEven ?
                repository.findByDayOfWeekAndStartTimeAndEvenTrue(day.getOrder(), startTime).map(converter::convert)
                :
                repository.findByDayOfWeekAndStartTimeAndEvenFalse(day.getOrder(), startTime).map(converter::convert);
    }
}
