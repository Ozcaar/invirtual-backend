package com.ozcaar.invirtual.Mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.DTOs.CreateDTOs.RoleCreateDTO;
import com.ozcaar.invirtual.DTOs.ReadDTOs.RoleReadDTO;
import com.ozcaar.invirtual.Models.RoleModel;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "role_id", ignore = true)
    RoleModel toEntity(RoleReadDTO dto);

    @Mapping(target = "role_id", ignore = true)
    RoleModel toEntity(RoleCreateDTO dto);
    
    RoleReadDTO toDTO(RoleModel user);

    List<RoleReadDTO> toDTOList(List<RoleModel> users);
}
