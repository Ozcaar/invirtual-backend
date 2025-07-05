package com.ozcaar.invirtual.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.DTOs.LoginDTO;
import com.ozcaar.invirtual.DTOs.CreateDTOs.UserCreateDTO;
import com.ozcaar.invirtual.DTOs.ReadDTOs.UserReadDTO;
import com.ozcaar.invirtual.DTOs.UpdateDTOs.UserUpdateDTO;
import com.ozcaar.invirtual.Exceptions.AlreadyExistsException;
import com.ozcaar.invirtual.Exceptions.NotFoundException;
import com.ozcaar.invirtual.Mappers.UserMapper;
import com.ozcaar.invirtual.Models.RoleModel;
import com.ozcaar.invirtual.Models.UserModel;
import com.ozcaar.invirtual.Models.ManyToManyTables.UserRoleModel;
import com.ozcaar.invirtual.Models.ManyToManyTables.CompositeIDs.UserRoleID;
import com.ozcaar.invirtual.Repositories.RoleRepository;
import com.ozcaar.invirtual.Repositories.UserRepository;
import com.ozcaar.invirtual.Repositories.ManyToManyRepositories.UserRoleRepository;
import com.ozcaar.invirtual.Security.JwtUtil;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    public String loginUser(LoginDTO dto) {
        // Authentication
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        // Set las login
        updateLastLogin(dto.getEmail());

        // Generate token
        String token = jwtUtil.generateToken(dto.getEmail());
        
        return token;
    }

    public void updateLastLogin(String email) {
        Optional<UserModel> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserModel user = optionalUser.get();
            user.setLast_login(LocalDateTime.now());
            userRepository.save(user);
        }
    }

    // Use only if the user already exists
    public void assignDefaultRole(UserModel user) {

        RoleModel defaultRole = roleRepository.findByName("USER")
        .orElseThrow(() -> new NotFoundException("No se encuentra el rol predeterminado 'USER'"));

        boolean alreadyAssigned = userRoleRepository.existsById(new UserRoleID(user.getUser_id(), defaultRole.getRole_id()));

        if (!alreadyAssigned) {
            UserRoleModel userRole = new UserRoleModel();
            userRole.setId(new UserRoleID(user.getUser_id(), defaultRole.getRole_id()));
            userRole.setUser(user);
            userRole.setRole(defaultRole);
            userRole.setAssignDate(LocalDateTime.now());
            userRole.setActive(true);

            userRoleRepository.save(userRole);
        }
    }

    // CRUDs

    // CREATE
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

        // First save the usaer to generate the ID
        UserModel savedUser = userRepository.save(user);

        // Set default role
        assignDefaultRole(savedUser);

        return userMapper.toDTO(savedUser);
    }


    // READ
    public UserReadDTO getUser(Integer id) {
        UserModel user = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No se encontró el usuario con el ID: " + id));

        return userMapper.toDTO(user);
    }

    public List<UserReadDTO> getAllUsers() {
        List<UserModel> users = (List<UserModel>) userRepository.findAll();
        return userMapper.toDTOList(users);
    }


    // UPDATE
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
                    userRole.setAssignDate(LocalDateTime.now());
                    userRole.setActive(true);

                    user.getUser_roles().add(userRole);
                    userRoleRepository.save(userRole);
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
