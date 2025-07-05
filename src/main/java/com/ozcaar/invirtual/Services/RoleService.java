package com.ozcaar.invirtual.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozcaar.invirtual.DTOs.CreateDTOs.RoleCreateDTO;
import com.ozcaar.invirtual.DTOs.ReadDTOs.RoleReadDTO;
import com.ozcaar.invirtual.DTOs.UpdateDTOs.RoleUpdateDTO;
import com.ozcaar.invirtual.Exceptions.AlreadyExistsException;
import com.ozcaar.invirtual.Exceptions.NotFoundException;
import com.ozcaar.invirtual.Mappers.RoleMapper;
import com.ozcaar.invirtual.Models.RoleModel;
import com.ozcaar.invirtual.Repositories.RoleRepository;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
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
