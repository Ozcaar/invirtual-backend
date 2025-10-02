package com.ozcaar.invirtual.user.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozcaar.invirtual.auth.service.JwtUtil;
import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.common.model.id.UserRoleID;
import com.ozcaar.invirtual.common.model.manytomany.UserRoleModel;
import com.ozcaar.invirtual.common.repository.UserRoleRepository;
import com.ozcaar.invirtual.role.model.RoleModel;
import com.ozcaar.invirtual.role.repository.RoleRepository;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;
import com.ozcaar.invirtual.user.dto.update.UserUpdateDTO;
import com.ozcaar.invirtual.user.mapper.UserMapper;
import com.ozcaar.invirtual.user.model.UserModel;
import com.ozcaar.invirtual.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    // Use only if the user already exists
    @Transactional
    public void assignDefaultRole(UserModel user) {

        RoleModel defaultRole = roleRepository.findByName("USER")
        .orElseThrow(() -> new NotFoundException("No se encuentra el rol predeterminado 'USER'"));

        boolean alreadyAssigned = userRoleRepository.existsById(new UserRoleID(user.getUser_id(), defaultRole.getRole_id()));

        if (!alreadyAssigned) {
            UserRoleModel userRole = new UserRoleModel();
            userRole.setId(new UserRoleID(user.getUser_id(), defaultRole.getRole_id()));
            userRole.setUser(user);
            userRole.setRole(defaultRole);
            userRole.setAssign_date(LocalDateTime.now());
            userRole.setActive(true);

            userRoleRepository.save(userRole);
        }
    }

    // CRUDs

    // CREATE
    @Transactional
    public UserReadDTO createUser(UserCreateDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new AlreadyExistsException("Ya existe un usuario con ese correo");
        }

        UserModel user = userMapper.toEntity(dto);

        // Encrypt password
        String hashedPassword = passwordEncoder.encode(dto.getPassword_plain());
        user.setPassword_hash(hashedPassword);

        // Active by default
        user.setActive(true);

        // First save the user to generate the ID
        UserModel savedUser = userRepository.save(user);

        // Set default role
        assignDefaultRole(savedUser);

        return userMapper.toDTO(savedUser);
    }

    // READ
    public UserReadDTO getUser(Integer id) {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No se encontró el usuario con el ID: " + id));

        boolean isAdminOrDev = jwtUtil.authenticatedUserUserIsAdminOrDev();
        
        if (!isAdminOrDev) {
            String userEmail = jwtUtil.getEmailOfAuthenticatedUser();
            if (userEmail == null || !userEmail.trim().equalsIgnoreCase(user.getEmail().trim())) {
                throw new AccessDeniedException("No tienes permiso para consultar este usuario");
            }
        }

        return userMapper.toDTO(user);
    }

    public List<UserReadDTO> getAllUsers() {
        List<UserModel> users = (List<UserModel>) userRepository.findAll();
        return userMapper.toDTOList(users);
    }

    // UPDATE
    @Transactional
    public UserReadDTO updateUser(Integer id, UserUpdateDTO dto) {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No se encontró el usuario con el ID: " + id));

        if (dto.getRole_ids() != null && !dto.getRole_ids().isEmpty()) {

            List<Integer> requestedRoleIds = dto.getRole_ids();

            // Remove the roles that are not in DTO
            user.getUser_roles().removeIf(userRole -> {
                boolean toRemove = !requestedRoleIds.contains(userRole.getRole().getRole_id());
                if (toRemove) {
                    userRoleRepository.delete(userRole);
                    // userRole.setActive(false);
                }
                return toRemove;
            });

            // Add new roles
            for (Integer roleId : dto.getRole_ids()) {
                RoleModel role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new NotFoundException("No se encontró el rol con el ID: " + roleId));

                boolean alreadyAssigned = user.getUser_roles().stream()
                    .anyMatch(ur -> ur.getRole().getRole_id().equals(role.getRole_id()));

                if (!alreadyAssigned) {
                    UserRoleModel userRole = new UserRoleModel();
                    userRole.setId(new UserRoleID(user.getUser_id(), role.getRole_id()));
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRole.setAssign_date(LocalDateTime.now());
                    userRole.setActive(true);

                    user.getUser_roles().add(userRole);
                    // userRoleRepository.save(userRole);
                }
            }
        } 
        // else {
        //     assignDefaultRole(user);
        // }

        if (dto.getFirst_name() != null) user.setFirst_name(dto.getFirst_name());
        if (dto.getLast_name() != null) user.setLast_name(dto.getLast_name());
        if (dto.getEmail() != null) user.setEmail(dto.getEmail());
        if (dto.getActive() != null) user.setActive(dto.getActive());

        UserModel updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    // DELETE
    public void deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("No se encontró el usuario con el ID: " + id);
        }
        userRepository.deleteById(id);

        // Soft delete
        // UserModel user = userRepository.findById(id)
        // .orElseThrow(() -> new NotFoundException("No se encontró el usuario con el ID: " + id));

        // user.setActive(false);
        // userRepository.save(user);
    }
}
