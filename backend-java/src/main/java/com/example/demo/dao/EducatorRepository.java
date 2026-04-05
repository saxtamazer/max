package com.example.demo.dao;

import com.example.demo.dao.entities.EducatorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EducatorRepository extends JpaRepository<EducatorModel, Integer> {
    Optional<EducatorModel> findByFullName(String fullName);
    boolean existsByFullName(String fullName);

    @Query("SELECT DISTINCT e.fullName FROM EducatorModel e")
    public List<String> getUniqueFullNameEducator();
}
