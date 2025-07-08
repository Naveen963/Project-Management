package com.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentProjectDTO {

    private long projectId;
    private String name;
    private  String description;
    private LocalDateTime createdAt;
}
