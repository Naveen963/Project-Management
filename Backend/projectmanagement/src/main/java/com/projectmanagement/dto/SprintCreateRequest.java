package com.projectmanagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SprintCreateRequest {
    private  String name;
    private LocalDate startDate;
    private  LocalDate endDate;
}
