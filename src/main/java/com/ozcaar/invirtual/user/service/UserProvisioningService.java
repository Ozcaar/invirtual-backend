package com.ozcaar.invirtual.user.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;

@Service
public class UserProvisioningService {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserProvisioningService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public UserModel getOrCreate(String supabase_id, String tokenEmail, Object userMetadata) {

        final String email;
        final String fullName;
        final String name;

        if (userMetadata instanceof Map<?, ?> map) {

            Object e = map.get("email");
            String metadataEmail = (e != null) ? e.toString() : null;
            email = (tokenEmail != null && !tokenEmail.isBlank()) ? tokenEmail : metadataEmail;

            Object fn = map.get("full_name");
            fullName = (fn != null) ? fn.toString() : null;

            Object n = map.get("name");
            name = (n != null) ? n.toString() : fullName;
        } else {
            email = tokenEmail;
            fullName = null;
            name = null;
        }

        String resolvedName = (name != null && !name.isBlank())
                ? name
                : (email != null && !email.isBlank() ? email.split("@", 2)[0] : "Usuario");

        return userRepository.findBySupabaseId(supabase_id)
                .orElseGet(() -> {

                    UserModel user = new UserModel();
                    user.setSupabaseId(supabase_id);

                    user.setName(resolvedName);
                    user.setFull_name(fullName);
                    user.setEmail(email);
                    user.setActive(true);
                    user.setLast_login(LocalDateTime.now());

                    UserModel savedUser = userRepository.save(user);
                    userService.assignDefaultRole(savedUser);

                    return savedUser;
                });
    }

}
