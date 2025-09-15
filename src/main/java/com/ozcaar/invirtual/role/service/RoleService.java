package com.ozcaar.invirtual.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.common.exception.global.AlreadyExistsException;
import com.ozcaar.invirtual.common.exception.global.NotFoundException;
import com.ozcaar.invirtual.role.dto.create.RoleCreateDTO;
import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;
import com.ozcaar.invirtual.role.dto.update.RoleUpdateDTO;
import com.ozcaar.invirtual.role.mapper.RoleMapper;
import com.ozcaar.invirtual.role.model.RoleModel;
import com.ozcaar.invirtual.role.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;
    private RoleMapper roleMapper;

    // CRUDs

    // CREATE
    public RoleReadDTO createRol(RoleCreateDTO dto) {
        if (roleRepository.findByName(dto.getName()).isPresent()) {
            throw new AlreadyExistsException("Ya existe un rol con ese nombre");
        }

        RoleModel rol = roleMapper.toEntity(dto);
        
        RoleModel saved = roleRepository.save(rol);
        return roleMapper.toDTO(saved);
    }

    // READ
    public RoleReadDTO getRole(Integer id) {
        RoleModel role = roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No se encontró el rol con el ID: " + id));

        return roleMapper.toDTO(role);
    }

    public List<RoleReadDTO> getAllRoles() {
        List<RoleModel> roles = (List<RoleModel>) roleRepository.findAll();
        return roleMapper.toDTOList(roles);
    }

    // UPDATE
    public RoleReadDTO updateRole(Integer id, RoleUpdateDTO dto) {
        RoleModel role = roleRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("No se encontró el rol con el ID" + id));

        if (dto.getName() != null) role.setName(dto.getName());

        RoleModel updated = roleRepository.save(role);
        return roleMapper.toDTO(updated);
    }

    // DELETE
    public void deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new NotFoundException("No se encontró el rol con el ID" + id);
        }
        roleRepository.deleteById(id);
    }

}
