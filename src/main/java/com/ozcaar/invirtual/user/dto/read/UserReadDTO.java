package com.ozcaar.invirtual.user.dto.read;

import java.util.List;

import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;

public class UserReadDTO {
    private Integer user_id;
    private String name;
    private String full_name;
    private String email;
    private List<RoleReadDTO> roles;

    // Getters & Setters
    public List<RoleReadDTO> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleReadDTO> roles) {
        this.roles = roles;
    }
    public Integer getUser_id() {
        return user_id;
    }
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFull_name() {
        return full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
