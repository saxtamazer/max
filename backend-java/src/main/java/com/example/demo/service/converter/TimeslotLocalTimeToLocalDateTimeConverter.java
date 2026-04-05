package com.example.demo.service.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class TimeslotLocalTimeToLocalDateTimeConverter implements Converter<LocalTime, LocalDateTime> {
    @Override
    public LocalDateTime convert(LocalTime source) {
        return LocalDateTime.now()
                .withHour(source.getHour())
                .withMinute(source.getMinute())
                .withSecond(0);
    }

    public LocalDateTime convertWithDayOfWeek(int dayOfWeek, LocalTime source) {
        return LocalDateTime.now()
                .with(DayOfWeek.of(dayOfWeek))
                .withHour(source.getHour())
                .withMinute(source.getMinute())
                .withSecond(0);
    }
}
