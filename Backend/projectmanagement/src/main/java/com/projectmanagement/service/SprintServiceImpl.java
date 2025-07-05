package com.projectmanagement.service;

import com.projectmanagement.dto.SprintCreateRequest;
import com.projectmanagement.exceptions.ResourceNotFoundException;
import com.projectmanagement.model.Issue;
import com.projectmanagement.model.Sprint;
import com.projectmanagement.repository.IssueRepository;
import com.projectmanagement.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SprintServiceImpl implements  SprintService{

    private final SprintRepository sprintRepository;
    private final IssueRepository issueRepository;

    @Override
    public Sprint createSprint(SprintCreateRequest request) {
        Sprint sprint = Sprint.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
        return sprintRepository.save(sprint);
    }

    @Override
    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    @Override
    public void assignIssueToSprint(Long issueId, Long sprintId) {

        Sprint sprint = sprintRepository.findById(sprintId)
                .orElseThrow(()->new ResourceNotFoundException("Sprint","sprintId",sprintId));

        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(()->new ResourceNotFoundException("Issue","issueId",sprintId));

        issue.setSprint(sprint);
        issueRepository.save(issue);

    }
}
