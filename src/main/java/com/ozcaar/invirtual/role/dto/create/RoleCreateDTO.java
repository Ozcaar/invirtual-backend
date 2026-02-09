package com.ozcaar.invirtual.role.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RoleCreateDTO {

    @NotNull(message = "El nombre es requerido.")
    @Size(min = 1, max = 10, message = "El nombre debe tener entre 1 y 10 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    private String name;

    // Getters & Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}