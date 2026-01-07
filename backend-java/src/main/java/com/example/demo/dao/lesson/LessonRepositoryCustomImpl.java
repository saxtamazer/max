package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LessonRepositoryCustomImpl implements LessonRepositoryCustom {
    private final EntityManager em;
    @Override
    public List<LessonModel> getAllLessonByEvenFilter(EvenFilter evenFilter) {
        String query = createQuery(evenFilter);
        List<LessonModel> lessons = em.createQuery(query).getResultList();
        return lessons;
    }

    private String createQuery(EvenFilter evenFilter) {
        return "SELECT l.id, l.group_id, l.subject_id, l.educator_id, l.auditorium_id, l.timeslot_id " +
                "FROM lesson AS l " +
                "JOIN timeslot AS t ON l.timeslot_id = t.id " +
                "WHERE t.even = " + evenFilter.isEven();
    }
}
