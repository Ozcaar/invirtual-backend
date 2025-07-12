package com.ozcaar.invirtual.invitation.service;

import java.util.ArrayList;
import java.util.List;

import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;

public class InvitationValidator {

    public static List<String> validate(InvitationCreateDTO dto) {
        
        List<String> errors = new ArrayList<>();

        if (dto.getInvitation_type_id() == null) {
            errors.add("Debe de escribir un tipo de validación");
        }

        if (dto.getName() == null || dto.getName().trim() != "" || dto.getName().length() < 5) {
            errors.add("El nombre no debe de estar vacío y debe tener al menos 5 caracteres.");
        }
        
        if (dto.getMax_people() == null || dto.getMax_people() < 1) {
            errors.add("La cantidad máxima de invitados debe ser mayor o igual a 1");
        }

        return errors;
    }

    // private static boolean isValidEmail(String email) {
    //     return email != null && email.contains("@");
    // }
}
