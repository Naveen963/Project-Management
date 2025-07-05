package com.projectmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SprintResponse {
    private Long id;
    private  String name;
    private LocalDate startDate;
    private  LocalDate endDate;
}
