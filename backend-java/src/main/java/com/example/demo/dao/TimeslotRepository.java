package com.example.demo.dao;

import com.example.demo.dao.entities.TimeslotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<TimeslotModel, Integer> {
    Optional<TimeslotModel> findByDayOfWeekAndStartTimeAndEvenTrue(int dayOfWeek, LocalTime startTime);
    Optional<TimeslotModel> findByDayOfWeekAndStartTimeAndEvenFalse(int dayOfWeek, LocalTime startTime);
}
