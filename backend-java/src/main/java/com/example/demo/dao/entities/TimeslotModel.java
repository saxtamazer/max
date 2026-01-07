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
    private boolean isEven;

    @Column(name = "even", nullable = false)
    private int dayOfWeek;

    @Column(name = "even", nullable = false)
    private int pairNumber;

    @Column(name = "even", nullable = false, columnDefinition = "time")
    private LocalTime startTime;

    @Column(name = "even", nullable = false, columnDefinition = "time")
    private LocalTime endTime;
}
