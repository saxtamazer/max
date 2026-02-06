package com.example.demo.dao;

import com.example.demo.dao.entities.EducatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EducatorRepository extends JpaRepository<EducatorModel, Integer> {
    Optional<EducatorModel> findByFullName(String fullName);
    boolean existsByFullName(String fullName);
}
