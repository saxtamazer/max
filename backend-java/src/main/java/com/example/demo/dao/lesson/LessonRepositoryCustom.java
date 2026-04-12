package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;
import com.example.demo.utils.filter.EvenFilter;
import com.example.demo.utils.filter.GroupFilter;

import java.util.List;

public interface LessonRepositoryCustom {
    List<LessonModel> findAllByEvenFilter(EvenFilter evenFilter);
    List<LessonModel> findAllByGroupFilter(GroupFilter groupFilter);
}
