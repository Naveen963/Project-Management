package com.projectmanagement.dto;

public interface IssueSummaryDTO {
    Long getId();
    String getTitle();
    String getStatus();
    String getPriority();
    String getProjectName();
    String getSprintName();
    String getAssigneeName();
}
