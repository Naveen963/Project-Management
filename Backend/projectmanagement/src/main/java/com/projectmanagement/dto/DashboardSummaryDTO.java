package com.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardSummaryDTO {

    private long totalProjects;
    private  long activeIssues;
    private long totalTeamMembers;
}
