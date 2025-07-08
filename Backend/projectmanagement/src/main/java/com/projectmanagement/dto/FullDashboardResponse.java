package com.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullDashboardResponse {
    public DashboardSummaryDTO summaryDTO;
    private List<RecentProjectDTO> recentProjectDTOS;
    private List<RecentIssueDTO> recentIssueDTOS;

}
