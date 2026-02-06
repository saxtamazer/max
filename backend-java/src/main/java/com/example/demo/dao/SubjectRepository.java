package com.example.demo.dao;

import com.example.demo.dao.entities.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel, Integer> {
    Optional<SubjectModel> findByNameAndTypeId(String name, int typeId);
    boolean existsByName(String name);
}
