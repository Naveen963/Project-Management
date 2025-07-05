package com.projectmanagement.service;

import com.projectmanagement.dto.SprintCreateRequest;
import com.projectmanagement.model.Sprint;

import java.util.List;

public interface SprintService {
    Sprint createSprint(SprintCreateRequest request);
    List<Sprint> getAllSprints();
    void assignIssueToSprint(Long issueId, Long sprintId);
}
