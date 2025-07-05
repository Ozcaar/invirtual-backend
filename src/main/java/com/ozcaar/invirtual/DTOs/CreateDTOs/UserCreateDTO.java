package com.ozcaar.invirtual.DTOs.CreateDTOs;

public class UserCreateDTO {
    private String first_name;
    private String last_name;
    private String email;
    private String password_plain;
    
    // Getters & Setters

    public String getPassword_plain() {
        return password_plain;
    }
    public void setPassword_plain(String password_plain) {
        this.password_plain = password_plain;
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
