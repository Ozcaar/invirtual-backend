package com.ozcaar.invirtual.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ozcaar.invirtual.common.exception.documentation.UserApiDoc;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;
import com.ozcaar.invirtual.user.dto.update.UserUpdateDTO;
import com.ozcaar.invirtual.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/users")
@Tag(name = "User Controller", description = "operaciones con usuarios")
@RequiredArgsConstructor
public class UserController implements UserApiDoc {

    private final UserService userService;

    // POST
    @PreAuthorize("hasRole('DEV')")
    @PostMapping()
    @Operation(summary = "Crear usuario", description = "- Se requiere el rol \"DEV\"")
    public ResponseEntity<UserReadDTO> createUser(@Valid @RequestBody UserCreateDTO user) {
        UserReadDTO created = userService.createUser(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/{id}")
    @Operation(summary = "Obtener usuario por ID", description = "")
    public ResponseEntity<UserReadDTO> getUser(@PathVariable Integer id) {
        UserReadDTO user = userService.getUser(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('DEV')")
    @GetMapping()
    @Operation(summary = "Obtener lista de usuarios", description = "")
    public ResponseEntity<List<UserReadDTO>> getAllUsers() {
        List<UserReadDTO> userList = userService.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // UPDATE
    @PreAuthorize("hasRole('DEV')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar usuario como admin", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<UserReadDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateDTO dto) {
        UserReadDTO updatedUser = userService.updateUser(id, dto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //DELETE
    @PreAuthorize("hasRole('DEV')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar usuario", description = "")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
