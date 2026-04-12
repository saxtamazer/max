package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<LessonModel, Integer>, LessonRepositoryCustom {
    boolean existsByGroupIdAndTimeslotId(int groupId, int timeslotId);
}
