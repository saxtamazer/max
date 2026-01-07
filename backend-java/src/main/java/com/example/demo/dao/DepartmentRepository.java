package com.example.demo.dao;

import com.example.demo.dao.entities.DepartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentModel, Integer> {
}
