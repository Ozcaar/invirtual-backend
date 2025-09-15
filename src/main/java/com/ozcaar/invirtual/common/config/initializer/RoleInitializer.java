package com.ozcaar.invirtual.common.config.initializer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.ozcaar.invirtual.role.model.RoleModel;
import com.ozcaar.invirtual.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("DEV");
        createRoleIfNotExists("USER");
        createRoleIfNotExists("SUPPORT");
        createRoleIfNotExists("COLABORATOR");
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