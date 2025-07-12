package com.ozcaar.invirtual.invitation.dto.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InvitationUpdateDTO {

    // public String envelope_id;

    // ? I think this field might not be editable because it'll be
    // ? selected when the user chose a plan.
    // public String invitation_type_id;

    // public String music_id;
    // public String sign_book_id;
    @NotNull(message = "El nombre es requerido.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    public String name;

    @NotNull(message = "El número máximo de invitados es requerido.")
    @Min(value = 1, message = "Debe haber al menos 1 invitado.")
    public Integer max_people;

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    public String slug_url;

    // Getters & setters

    // public String getInvitation_type_id() {
    //     return invitation_type_id;
    // }
    // public void setInvitation_type_id(String invitation_type_id) {
    //     this.invitation_type_id = invitation_type_id;
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
    public String getSlug_url() {
        return slug_url;
    }
    public void setSlug_url(String slug_url) {
        this.slug_url = slug_url;
    }    
}
