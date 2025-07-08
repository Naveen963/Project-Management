package com.projectmanagement.service;

import com.projectmanagement.dto.DashboardSummaryDTO;
import com.projectmanagement.dto.FullDashboardResponse;
import com.projectmanagement.dto.RecentIssueDTO;
import com.projectmanagement.dto.RecentProjectDTO;
import com.projectmanagement.model.Issue;
import com.projectmanagement.model.IssueStatus;
import com.projectmanagement.model.Project;
import com.projectmanagement.repository.IssueRepository;
import com.projectmanagement.repository.ProjectMemberRepository;
import com.projectmanagement.repository.ProjectRepository;
import com.projectmanagement.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private  final ProjectRepository projectRepository;
    private final IssueRepository issueRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public DashboardService(ProjectRepository projectRepository,IssueRepository issueRepository, ProjectMemberRepository projectMemberRepository){
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
        this.projectMemberRepository = projectMemberRepository;
    }

    public DashboardSummaryDTO getSummary(){
        long totalProjects = projectRepository.count();
        long activeIssues = issueRepository.countByStatus(IssueStatus.OPEN);
        Project latestProject = projectRepository.findTopByOrderByCreatedAtDesc();
        long teammembers = (latestProject!=null) ? projectMemberRepository.countByProjectId(latestProject.getId())
                :0;
        return new DashboardSummaryDTO(totalProjects,activeIssues,teammembers);
    }

    public FullDashboardResponse getFullDashboard(){

        return  new FullDashboardResponse(
        getSummary(),
        getRecentProjects(),
        getRecentIssues());
    }

    private List<RecentIssueDTO> getRecentIssues() {
        List<Issue> issues = issueRepository.findTop3ByOrderByCreatedAtDesc();
        return issues.stream()
                .map(p->new RecentIssueDTO(p.getId(),p.getTitle(),p.getStatus().name(),p.getPriority().name(),p.getCreatedAt()))
                .collect(Collectors.toList());
    }

    public List<RecentProjectDTO> getRecentProjects(){

        List<Project> projects = projectRepository.findTop3ByOrderByCreatedAtDesc();
        return projects.stream()
                .map(p->new RecentProjectDTO(p.getId(),p.getName(),p.getDescription(),p.getCreatedAt()))
                .collect(Collectors.toList());
    }
}
