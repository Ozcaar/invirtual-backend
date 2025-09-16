package com.ozcaar.invirtual.common.config.initializer;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ozcaar.invirtual.guest.model.GuestGroupModel;
import com.ozcaar.invirtual.guest.repository.GuestGroupRepository;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GuestGroupInitializer implements CommandLineRunner {

    private final GuestGroupRepository guestGroupRepository;
    private final InvitationTypeRepository invitationTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        // Crear tipos si no existen
        InvitationTypeModel boda = invitationTypeRepository.findByName("BODA")
            .orElseThrow(() -> new RuntimeException("Tipo BODA no existe"));

        InvitationTypeModel cumple = invitationTypeRepository.findByName("CUMPLEAÑOS")
            .orElseThrow(() -> new RuntimeException("Tipo CUMPLEAÑOS no existe"));

        // Crear grupos y asociarlos
        createGuestGroupIfNotExists("FAMILIA", "Familia cercana", Set.of(boda, cumple));
        createGuestGroupIfNotExists("AMIGOS", "Amigos del anfitrión", Set.of(boda, cumple));
        createGuestGroupIfNotExists("VIP", "Invitados especiales", Set.of(boda));
        createGuestGroupIfNotExists("OTRO", "Otros invitados", Set.of(boda, cumple));
    }

    private void createGuestGroupIfNotExists(String name, String description, Set<InvitationTypeModel> types) {
        guestGroupRepository.findByName(name).orElseGet(() -> {
            GuestGroupModel group = new GuestGroupModel();
            group.setName(name);
            group.setDescription(description);
            group.setInvitationTypes(types);
            guestGroupRepository.save(group);
            System.out.println("Grupo de invitados creado: " + name);
            return group;
        });
    }
}