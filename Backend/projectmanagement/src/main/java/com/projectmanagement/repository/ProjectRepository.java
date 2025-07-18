package com.projectmanagement.repository;

import com.projectmanagement.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    List<Project> findTop3ByOrderByCreatedAtDesc();
    
    List<Project> findByNameContainingIgnoreCase(String name);

    Project findTopByOrderByCreatedAtDesc();
}
