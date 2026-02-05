package com.example.demo.dao.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lesson")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "group_id", "timeslot_id"})
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "group_id", nullable = false)
    int groupId;

    @Column(name = "subject_id", nullable = false)
    int subjectId;

    @Column(name = "timeslot_id", nullable = false)
    int timeslotId;

    @ManyToMany
    @JoinTable(
            name = "lesson_educator",
            joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "educator_id", referencedColumnName = "id")
    )
    List<EducatorModel> educators = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "lesson_auditorium",
            joinColumns = @JoinColumn(name = "lesson_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "auditorium", referencedColumnName = "id")
    )
    List<AuditoriumModel> auditoriums = new ArrayList<>();

    public void addEducator(EducatorModel educator) {
        this.educators.add(educator);
    }

    public void addAuditorium(AuditoriumModel auditorium) {
        this.auditoriums.add(auditorium);
    }
}
