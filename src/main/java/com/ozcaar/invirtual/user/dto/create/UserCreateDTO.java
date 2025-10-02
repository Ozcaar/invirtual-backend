package com.ozcaar.invirtual.user.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotNull(message = "El nombre es requerido.")
    private String first_name;

    @Size(min = 3, max = 100, message = "El apellido debe tener entre 3 y 100 caracteres.")
    @NotNull(message = "El apellido es requerido.")
    private String last_name;

    @NotNull(message = "El correo es requerido.")
    @Email(message = "Debe ingresar un correo válido.")
    private String email;

    @NotNull(message = "La contraseña es requerida.")
    // Require at least a lowercap, a capital letter, a number, 8 character length
    @Schema(
        description = "Contraseña del usuario. Debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números.",
        example = "MiP4sswordSegura",
        pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
        minLength = 8
    )
    @Pattern(
        // regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-={}:;\"',.<>?]).{8,}$", // Require at least one special character
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
        message = "La contraseña debe tener al menos 8 caracteres, incluyendo mayúsculas, minúsculas y números."
    )
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
