package com.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecentIssueDTO {

   private long issueId;
   private String title;
   private String status;
   private String priority;
   private LocalDateTime createdAt;
}
