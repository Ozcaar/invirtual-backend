package com.ozcaar.invirtual.user.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.common.model.manytomany.UserRoleModel;
import com.ozcaar.invirtual.role.dto.read.RoleReadDTO;
import com.ozcaar.invirtual.user.dto.create.UserCreateDTO;
import com.ozcaar.invirtual.user.dto.read.UserReadDTO;
import com.ozcaar.invirtual.user.dto.update.UserUpdateDTO;
import com.ozcaar.invirtual.user.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    @Mapping(target = "last_login", ignore = true)
    @Mapping(target = "user_roles", ignore = true)
    UserModel toEntity(UserCreateDTO dto);

    @Mapping(target = "password_hash", ignore = true)
    @Mapping(target = "user_id", ignore = true)
    @Mapping(target = "last_login", ignore = true)
    @Mapping(target = "user_roles", ignore = true)
    UserModel toEntity(UserUpdateDTO dto);
    
    @Mapping(target = "roles", expression = "java(mapRoles(user.getUser_roles()))")
    UserReadDTO toDTO(UserModel user);

    List<UserReadDTO> toDTOList(List<UserModel> users);

    // default List<String> mapRoles(Set<UserRoleModel> userRoles) {
    // if (userRoles == null) return List.of();
    //     return userRoles.stream()
    //         .map(ur -> ur.getRole().getName())
    //         .toList();
    // }

    default List<RoleReadDTO> mapRoles(List<UserRoleModel> userRoles) {
        if (userRoles == null) return Collections.emptyList();
        return userRoles.stream()
            .filter(UserRoleModel::getActive)
            .map(ur -> {
                RoleReadDTO dto = new RoleReadDTO();
                dto.setRole_id(ur.getRole().getRole_id());
                dto.setName(ur.getRole().getName());
                return dto;
            })
            .collect(Collectors.toList());
    }
}
