package com.ozcaar.invirtual.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ozcaar.invirtual.Models.RoleModel;
import com.ozcaar.invirtual.Repositories.RoleRepository;

@Component
public class RoleInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("DEV");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.findByName(roleName).isPresent()) {
            RoleModel role = new RoleModel();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Rol creado: " + roleName);
        }
    }
}