package com.ozcaar.invirtual.auth.dto;

public class LoginDTO {
    
    private String email;
    private String password;

    // Getters & setters

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
