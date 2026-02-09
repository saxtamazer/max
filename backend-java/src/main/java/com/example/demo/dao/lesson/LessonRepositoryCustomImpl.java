package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;
import com.example.demo.utils.filter.EvenFilter;
import com.example.demo.utils.filter.GroupFilter;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class LessonRepositoryCustomImpl implements LessonRepositoryCustom {
    private final EntityManager em;

    @Override
    public List<LessonModel> findAllByGroupFilter(GroupFilter groupFilter) {
        String query = "SELECT l FROM LessonModel AS l, StudentGroupModel AS g " +
                "LEFT JOIN FETCH l.educators " +
                "LEFT JOIN FETCH l.auditoriums " +
                "WHERE l.groupId = g.id AND g.name = :name";

        return em.createQuery(query, LessonModel.class)
                .setParameter("name", groupFilter.getGroupName())
                .getResultList();
    }

    @Override
    public List<LessonModel> findAllByEvenFilter(EvenFilter evenFilter) {
        String query = "SELECT l FROM LessonModel AS l, TimeslotModel AS t " +
                "LEFT JOIN FETCH l.educators " +
                "LEFT JOIN FETCH l.auditoriums " +
                "WHERE l.timeslotId = t.id AND t.even = :isEven";
        return em.createQuery(query, LessonModel.class)
                .setParameter("isEven", evenFilter.isEven())
                .getResultList();
    }
}
