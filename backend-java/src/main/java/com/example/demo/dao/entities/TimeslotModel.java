package com.example.demo.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "timeslot")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
public class TimeslotModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "even", nullable = false)
    private boolean even;

    @Column(name = "day_of_week", nullable = false)
    private int dayOfWeek;

    @Column(name = "pair_number", nullable = false)
    private int pairNumber;

    @Column(name = "start_time", nullable = false, columnDefinition = "time")
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false, columnDefinition = "time")
    private LocalTime endTime;
}
