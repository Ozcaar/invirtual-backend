package com.ozcaar.invirtual.invitation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.model.InvitationModel;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    @Mapping(target = "invitation_uuid", ignore = true)
    // Temporaly ignore
    @Mapping(target = "invitation_type_id", ignore = true)
    // @Mapping(target = "music_id", ignore = true)
    // @Mapping(target = "sign_book_id", ignore = true)
    @Mapping(target = "max_people", ignore = true)

    @Mapping(target = "slug_url", ignore = true)
    @Mapping(target = "creation_date", ignore = true)
    @Mapping(target = "limit_date", ignore = true)
    @Mapping(target = "is_demo", ignore = true)
    @Mapping(target = "active", ignore = true)
    InvitationModel toEntity(InvitationCreateDTO dto);

    InvitationReadDTO toDTO(InvitationModel invitation);

    List<InvitationReadDTO> toDTOList(List<InvitationModel> invitations);

}
