package com.ozcaar.invirtual.user.dto.update;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    private String first_name;

    @Size(min = 3, max = 100, message = "El apellido debe tener entre 3 y 100 caracteres.")
    private String last_name;

    @Email(message = "Debe ingresar un correo válido.")
    private String email;

    private Boolean active;

    private List<Integer> role_ids = new ArrayList<>();
    
    // Getters & Setters

    public List<Integer> getRole_ids() {
        return role_ids;
    }
    public void setRole_ids(List<Integer> role_ids) {
        this.role_ids = role_ids;
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
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }


}
