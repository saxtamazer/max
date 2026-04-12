package com.example.demo.service.converter.modeltodto;

import com.example.demo.dao.entities.TimeslotModel;
import com.example.demo.service.dto.TimeslotDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class TimeslotModelToTimeslotDTOConverter implements Converter<TimeslotModel, TimeslotDTO> {
    @Override
    public TimeslotDTO convert(TimeslotModel source) {
        return source == null ?
                null
                :
                new TimeslotDTO(
                        source.getId(),
                        source.isEven(),
                        source.getDayOfWeek(),
                        source.getPairNumber(),
                        source.getStartTime(),
                        source.getEndTime()
                );
    }
}
