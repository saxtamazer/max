package com.example.demo.dao;

import com.example.demo.dao.entities.TimeslotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface TimeslotRepository extends JpaRepository<TimeslotModel, Integer> {
    Optional<TimeslotModel> findByDayOfWeekAndStartTimeAndEven(int dayOfWeek, LocalTime startTime, boolean even);
}
