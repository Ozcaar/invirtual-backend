package com.ozcaar.invirtual.invitation.dto.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class InvitationUpdateDTO {

    // public String envelope_id;

    // ? I think this field might not be editable because it'll be
    // ? selected when the user chose a plan.
    // public String invitationTypeId;

    // public String music_id;
    // public String sign_book_id;
    @NotNull(message = "El nombre es requerido.")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    public String name;

    @NotNull(message = "El número máximo de invitados es requerido.")
    @Min(value = 1, message = "Debe haber al menos 1 invitado.")
    public Integer maxPeople;

    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres.")
    @NotBlank(message = "El nombre no pueden ser solo caracteres en blanco.")
    public String slugUrl;

    // Getters & setters

    // public String getInvitationTypeId() {
    // return invitationTypeId;
    // }
    // public void setInvitationTypeId(String invitationTypeId) {
    // this.invitationTypeId = invitationTypeId;
    // }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getSlugUrl() {
        return slugUrl;
    }

    public void setSlugUrl(String slugUrl) {
        this.slugUrl = slugUrl;
    }
}
