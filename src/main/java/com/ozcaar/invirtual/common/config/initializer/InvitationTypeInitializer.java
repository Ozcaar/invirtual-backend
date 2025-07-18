package com.ozcaar.invirtual.common.config.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.invitation.repository.InvitationTypeRepository;

@Component
public class InvitationTypeInitializer implements CommandLineRunner {
    
    @Autowired
    private InvitationTypeRepository invitationTypeRepository;

    @Override
    public void run(String ...args) throws Exception {
        createInvitationTypeIfNotExists("BODA", "");
        createInvitationTypeIfNotExists("CUMPLEAÑOS", "");
        createInvitationTypeIfNotExists("EMPRESARIAL", "");
        createInvitationTypeIfNotExists("INFANTIL", "");
        createInvitationTypeIfNotExists("OTRO", "");
    }

    private void createInvitationTypeIfNotExists(String invitationName, String invitationDescription) {
        if (!invitationTypeRepository.findByName(invitationName).isPresent()) {
            InvitationTypeModel invitationType = new InvitationTypeModel();
            invitationType.setName(invitationName);
            invitationType.setDescription(invitationDescription);
            invitationTypeRepository.save(invitationType);
            System.out.println("Tipo de invitación creado: " + invitationName);
        }
    }

}
