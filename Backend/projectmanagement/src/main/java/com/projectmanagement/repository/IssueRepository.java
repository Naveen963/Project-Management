package com.projectmanagement.repository;

import com.projectmanagement.dto.IssueSummaryDTO;
import com.projectmanagement.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IssueRepository extends JpaRepository<Issue,Long> {

    List<Issue> findByReportedBy(User user);

    List<Issue> findByAssignedTo(User user);

    List<Issue> findByProject(Project project);

    List<Issue> findBySprint(Sprint sprint);

    List<Issue> findByStatus(IssueStatus status);

    List<Issue> findByPriority(IssuePriority issuePriority);

    List<Issue> findByProjectAndSprint(Project project, Sprint sprint);
    List<Issue> findByAssignedToAndStatus(User user, IssueStatus issueStatus);
    List<Issue> findBySprintAndStatus(Sprint sprint,IssueStatus issueStatus);


    @Query("""
            Select
            i.issueId AS id,
            i.title AS title,
            i.status AS status,
            i.priority AS priority,
            p.name AS projectName,
            s.name AS sprintName,
            a.userName AS assigneeName
            From Issue i
            Left Join i.project p
            Left Join i.sprint s
            Left Join i.assignedTo a
            """)
    List<IssueSummaryDTO> fetchAllIssueSummaries();
}
