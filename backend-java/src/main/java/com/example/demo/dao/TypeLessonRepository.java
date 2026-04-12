package com.example.demo.dao;

import com.example.demo.dao.entities.TypeLessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeLessonRepository extends JpaRepository<TypeLessonModel, Integer> {
    Optional<TypeLessonModel> findByName(String name);
}
