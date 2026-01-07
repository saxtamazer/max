package com.example.demo.dao;

import com.example.demo.dao.entities.TimeslotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeslotRepository extends JpaRepository<TimeslotModel, Integer> {

}
