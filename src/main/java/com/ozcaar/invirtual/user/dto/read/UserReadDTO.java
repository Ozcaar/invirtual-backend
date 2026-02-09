package com.ozcaar.invirtual.user.dto.read;

import java.util.List;

import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;

public class UserReadDTO {
    private String supabaseId;
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

    public String getSupabaseId() {
        return supabaseId;
    }

    public void setSupabaseId(String supabaseId) {
        this.supabaseId = supabaseId;
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
