package com.example.demo.dao;

import com.example.demo.dao.entities.TypeLessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeLessonRepository extends JpaRepository<TypeLessonModel, Integer> {
}
