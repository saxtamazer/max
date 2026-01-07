package com.example.demo.dao.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "group_id", "timeslot_id"})
@AllArgsConstructor
@NoArgsConstructor
public class LessonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_id", nullable = false)
    private int groupId;

    @Column(name = "subject_id", nullable = false)
    private int subjectId;

    @Column(name = "educator_id", nullable = false)
    private int educatorId;

    @Column(name = "auditorium_id", nullable = false)
    private int auditoriumId;

    @Column(name = "timeslot_id", nullable = false)
    private int timeslotId;
}
