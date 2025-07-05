package com.projectmanagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Sprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sprintId;


    private String name;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "sprint",cascade = CascadeType.ALL)
    private List<Issue> issues;
}
