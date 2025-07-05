package com.projectmanagement.repository;

import com.projectmanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintRepository extends JpaRepository<Sprint,Long> {

}
