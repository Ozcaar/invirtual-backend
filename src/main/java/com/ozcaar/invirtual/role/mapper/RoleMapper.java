package com.ozcaar.invirtual.role.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.role.dto.create.RoleCreateDTO;
import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;
import com.ozcaar.invirtual.role.model.RoleModel;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "role_id", ignore = true)
    RoleModel toEntity(RoleReadDTO dto);

    @Mapping(target = "role_id", ignore = true)
    RoleModel toEntity(RoleCreateDTO dto);
    
    RoleReadDTO toDTO(RoleModel user);

    List<RoleReadDTO> toDTOList(List<RoleModel> users);
}
