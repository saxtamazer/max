package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;

import java.util.List;

public interface LessonRepositoryCustom {
    List<LessonModel> findAllLessonByEvenFilter(EvenFilter evenFilter);
}
