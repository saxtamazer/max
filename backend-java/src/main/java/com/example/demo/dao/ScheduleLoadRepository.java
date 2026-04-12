package com.example.demo.dao;

import com.example.demo.service.dto.LoadAuditroiumDTO;
import com.example.demo.service.dto.LoadEducatorDTO;
import com.example.demo.service.dto.LoadStudentGroupDTO;
import jakarta.persistence.EntityManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class ScheduleLoadRepository {
    EntityManager entityManager;

    public List<LoadAuditroiumDTO> getReportLoadAuditorium(String block, String ident) {
        String nativeQuery = "SELECT t.id, t.even, t.day_of_week, t.pair_number, t.start_time, t.end_time, STRING_AGG(g.name, ';') " +
                "FROM auditorium AS a " +
                "JOIN lesson_auditorium AS la ON a.id = la.auditorium_id " +
                "JOIN lesson AS l ON la.lesson_id = l.id " +
                "JOIN timeslot AS t ON l.timeslot_id = t.id " +
                "JOIN student_group AS g ON l.group_id = g.id " +
                "WHERE a.block = ?1 AND a.ident = ?2 " +
                "GROUP BY t.id, t.even, t.day_of_week, t.pair_number, t.start_time, t.end_time;";

        return entityManager.createNativeQuery(nativeQuery, LoadAuditroiumDTO.class)
                .setParameter(1, block)
                .setParameter(2, ident)
                .getResultList();
    }

    public List<LoadEducatorDTO> getReportLoadEducator(String fullName) {
        String nativeQuery = "SELECT t.even, t.day_of_week, t.pair_number, st.name, s.name, STRING_AGG(g.name, ';') AS groups, STRING_AGG(DISTINCT a.block || '-' || a.ident, ';') AS auditoriums " +
                "FROM educator AS e " +
                "JOIN lesson_educator AS le ON e.id = le.educator_id " +
                "JOIN lesson AS l ON le.lesson_id = l.id " +
                "JOIN student_group AS g ON l.group_id = g.id " +
                "JOIN lesson_auditorium AS la ON l.id = la.lesson_id " +
                "JOIN auditorium AS a ON la.auditorium_id = a.id " +
                "JOIN timeslot AS t ON l.timeslot_id = t.id " +
                "JOIN subject AS s ON l.subject_id = s.id " +
                "JOIN type_lesson AS st ON s.type_id = st.id " +
                "WHERE e.full_name = ?1 " +
                "GROUP BY s.name, t.even, t.day_of_week, t.pair_number, st.name;";

        return entityManager.createNativeQuery(nativeQuery, LoadEducatorDTO.class)
                .setParameter(1, fullName)
                .getResultList();
    }

    public List<LoadStudentGroupDTO> getReportLoadStudentGroup(String name) {
        String nativeQuery = "SELECT t.id, t.even, t.day_of_week, t.pair_number, s.name, st.name, STRING_AGG(DISTINCT a.block || '-' || a.ident, ';') AS auditoriums " +
                "FROM student_group AS g " +
                "JOIN lesson AS l ON g.id = l.group_id " +
                "JOIN timeslot AS t ON l.timeslot_id = t.id " +
                "JOIN subject AS s ON l.subject_id = s.id " +
                "JOIN type_lesson AS st ON s.type_id = st.id " +
                "JOIN lesson_auditorium AS la ON l.id = la.lesson_id " +
                "JOIN auditorium AS a ON la.auditorium_id = a.id " +
                "WHERE g.name = ?1 " +
                "GROUP BY t.id, t.even, t.day_of_week, t.pair_number, s.name, st.name;";

        return entityManager.createNativeQuery(nativeQuery, LoadStudentGroupDTO.class)
                .setParameter(1, name)
                .getResultList();
    }
}
