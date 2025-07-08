package com.projectmanagement.repository;

import com.projectmanagement.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {

    long countByProjectId(Long projectId);
}
