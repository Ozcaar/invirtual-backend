package com.ozcaar.invirtual.invitation.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ozcaar.invirtual.invitation.dto.create.InvitationCreateDTO;
import com.ozcaar.invirtual.invitation.dto.read.InvitationReadDTO;
import com.ozcaar.invirtual.invitation.model.InvitationModel;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    @Mapping(target = "invitationUuid", ignore = true)
    // Temporaly ignore
    @Mapping(target = "invitation_type_id", ignore = true)
    // @Mapping(target = "music_id", ignore = true)
    // @Mapping(target = "sign_book_id", ignore = true)
    @Mapping(target = "maxPeople", ignore = true)

    @Mapping(target = "slugUrl", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "limitDate", ignore = true)
    @Mapping(target = "isDemo", ignore = true)
    @Mapping(target = "active", ignore = true)
    InvitationModel toEntity(InvitationCreateDTO dto);

    InvitationReadDTO toDTO(InvitationModel invitation);

    List<InvitationReadDTO> toDTOList(List<InvitationModel> invitations);

}
