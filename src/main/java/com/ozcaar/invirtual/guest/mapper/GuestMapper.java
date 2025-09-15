package com.ozcaar.invirtual.guest.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.guest.dto.create.GuestCreateDTO;
import com.ozcaar.invirtual.guest.dto.read.GuestReadDTO;
import com.ozcaar.invirtual.guest.model.GuestModel;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    @Mapping(target = "guest_id", ignore = true)
    @Mapping(target = "confirmed_date", ignore = true)
    @Mapping(target = "confirmed", ignore = true)
    @Mapping(target = "link_opened", ignore = true)
    @Mapping(target = "guest_group", ignore = true)
    GuestModel toEntity(GuestCreateDTO GuestCreateDTO);

    // GuestModel toEntity(GuestUpdateDTO)

    // @Mapping(target = "roles", expression = "java(mapRoles(user.getUser_roles()))")
    GuestReadDTO toDTO(GuestModel guest);

    List<GuestReadDTO> toDTOList(List<GuestModel> guests);
}
