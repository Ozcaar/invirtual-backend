package com.ozcaar.invirtual.role.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ozcaar.invirtual.common.exception.documentation.RoleApiDoc;
import com.ozcaar.invirtual.role.dto.create.RoleCreateDTO;
import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;
import com.ozcaar.invirtual.role.dto.update.RoleUpdateDTO;
import com.ozcaar.invirtual.role.service.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin/role")
@Tag(name = "Role Controller", description = "operaciones con roles")
@RequiredArgsConstructor
public class RoleController implements RoleApiDoc {

    private final RoleService roleService;

    // POST
    @PreAuthorize("hasRole('DEV')")
    @PostMapping()
    @Operation(summary = "Crear Rol", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<RoleReadDTO> createRole(@RequestBody RoleCreateDTO role) {
        RoleReadDTO created = roleService.createRol(role);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // GET
    @GetMapping("/{id}")
    @Operation(summary = "Obtener rol único", description = "")
    public ResponseEntity<RoleReadDTO> getRole(@PathVariable Integer id) {
        RoleReadDTO role = roleService.getRole(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @GetMapping("/list")
    @Operation(summary = "Obtener lista de roles", description = "")
    public ResponseEntity<List<RoleReadDTO>> getAllRoles() {
        List<RoleReadDTO> roleList = roleService.getAllRoles();
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }

    // UPDATE
    @PreAuthorize("hasRole('DEV')")
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar rol", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<RoleReadDTO> updateRole(@PathVariable Integer id, @RequestBody RoleUpdateDTO dto) {
        RoleReadDTO updated = roleService.updateRole(id, dto);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    //DELETE
    @PreAuthorize("hasRole('DEV')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar rol", description = "- Se requiere rol \"DEV\"")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
