package com.ozcaar.invirtual.guest.dto.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GuestCreateDTO {
    
    // public Integer guest_id;
    // public Integer invitated_group_id;
    // public Integer sign_book_id
    
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotNull(message = "El nombre es requerido.")
    private String name;

    @NotNull(message = "El correo es requerido.")
    @Email(message = "Debe ingresar un correo válido.")
    private String email;
    
    // private Integer guest_group_id;
    private Boolean enabled;

    // Getters & setters

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
