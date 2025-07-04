package com.projectmanagement.dto;

import com.projectmanagement.model.AppRole;
import com.projectmanagement.model.Role;
import lombok.Data;

@Data
public class JwtResponse {
    private  String token;
    private  String name;
    private Role role;
}
