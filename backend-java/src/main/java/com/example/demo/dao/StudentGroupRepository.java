package com.example.demo.dao;

import com.example.demo.dao.entities.StudentGroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentGroupRepository extends JpaRepository<StudentGroupModel, Integer> {
    Optional<StudentGroupModel> findByName(String name);
    boolean existsByName(String name);

    @Query("SELECT DISTINCT g.name FROM StudentGroupModel AS g")
    public List<String> getGroupsName();
}
