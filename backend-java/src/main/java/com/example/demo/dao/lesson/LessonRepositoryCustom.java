package com.example.demo.dao.lesson;

import com.example.demo.dao.entities.LessonModel;

import java.util.List;

public interface LessonRepositoryCustom {
    List<LessonModel> getAllLessonByEvenFilter(EvenFilter evenFilter);
}
