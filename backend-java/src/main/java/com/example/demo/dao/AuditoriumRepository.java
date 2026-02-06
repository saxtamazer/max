package com.example.demo.dao;

import com.example.demo.dao.entities.AuditoriumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuditoriumRepository extends JpaRepository<AuditoriumModel, Integer> {
    boolean existsByBlockAndIdent(String block, String ident);
    Optional<AuditoriumModel> getAuditoriumModelByBlockAndIdent(String block, String ident);
}
