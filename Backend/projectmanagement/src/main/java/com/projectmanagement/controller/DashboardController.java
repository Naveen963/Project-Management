package com.projectmanagement.controller;

import com.projectmanagement.dto.FullDashboardResponse;
import com.projectmanagement.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/full")
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','DEVELOPER','TESTER')")
    public FullDashboardResponse getFullDashboard(){
        return  dashboardService.getFullDashboard();
    }
}
