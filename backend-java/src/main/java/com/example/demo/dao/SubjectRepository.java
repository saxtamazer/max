package com.example.demo.dao;

import com.example.demo.dao.entities.SubjectModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectModel, Integer> {
}
