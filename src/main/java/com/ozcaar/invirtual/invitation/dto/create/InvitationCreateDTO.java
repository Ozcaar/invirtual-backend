package com.ozcaar.invirtual.invitation.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InvitationCreateDTO {
    @NotNull(message = "El tipo de invitación es requerido.")
    private Integer invitation_type_id;

    // private Integer music_id;
    // private Integer sign_book_id;

    @NotNull(message = "El nombre es requerido.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    private String name;

    @NotNull(message = "El número máximo de invitados es requerido.")
    @Min(value = 1, message = "Debe haber al menos 1 invitado.")
    private Integer max_people;
    
    // Getters & setters

    public Integer getInvitation_type_id() {
        return invitation_type_id;
    }
    public void setInvitation_type_id(Integer invitation_type_id) {
        this.invitation_type_id = invitation_type_id;
    }
    // public Integer getMusic_id() {
    //     return music_id;
    // }
    // public void setMusic_id(Integer music_id) {
    //     this.music_id = music_id;
    // }
    // public Integer getSign_book_id() {
    //     return sign_book_id;
    // }
    // public void setSign_book_id(Integer sign_book_id) {
    //     this.sign_book_id = sign_book_id;
    // }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getMax_people() {
        return max_people;
    }
    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }
}
