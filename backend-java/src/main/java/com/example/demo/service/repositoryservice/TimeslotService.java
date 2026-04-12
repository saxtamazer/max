package com.example.demo.service.repositoryservice;

import com.example.demo.api.json.TimeResponse;
import com.example.demo.configuration.EnvProperties;
import com.example.demo.dao.TimeslotRepository;
import com.example.demo.service.converter.TimeslotLocalTimeToLocalDateTimeConverter;
import com.example.demo.service.converter.modeltodto.TimeslotModelToTimeslotDTOConverter;
import com.example.demo.service.dto.TimeslotDTO;
import com.example.demo.utils.CustomDayOfWeek;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeslotService {
    TimeslotRepository repository;
    TimeslotModelToTimeslotDTOConverter converter;
    EnvProperties envProperties;
    TimeslotLocalTimeToLocalDateTimeConverter timeConverter;

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

    public List<TimeResponse> getPairTimeInfo() {
        List<TimeResponse> response = new ArrayList<>(envProperties.getPairPerDay());
        TimeslotDTO timeslot;
        for (int i = 1; i <= envProperties.getPairPerDay(); i++) {
            timeslot = this.getTimeslotById(i);
            response.add(new TimeResponse(
                    timeslot.getPairNumber(),
                    timeConverter.convertWithDayOfWeek(timeslot.getDayOfWeek(), timeslot.getStartTime()),
                    timeConverter.convertWithDayOfWeek(timeslot.getDayOfWeek(), timeslot.getEndTime())
            ));
        }
        return response;
    }
}
