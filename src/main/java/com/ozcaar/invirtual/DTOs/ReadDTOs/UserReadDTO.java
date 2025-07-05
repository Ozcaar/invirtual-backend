package com.ozcaar.invirtual.DTOs.ReadDTOs;

import java.util.List;

public class UserReadDTO {
    private Integer user_id;
    private String first_name;
    private String last_name;
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
    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
