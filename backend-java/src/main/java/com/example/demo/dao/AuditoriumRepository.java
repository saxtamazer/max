package com.example.demo.dao;

import com.example.demo.dao.entities.AuditoriumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditoriumRepository extends JpaRepository<AuditoriumModel, Integer> {
}
