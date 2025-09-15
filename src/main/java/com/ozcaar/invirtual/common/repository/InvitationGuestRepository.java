package com.ozcaar.invirtual.common.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.common.model.id.InvitationGuestID;
import com.ozcaar.invirtual.common.model.manytomany.InvitationGuestModel;

public interface InvitationGuestRepository extends CrudRepository<InvitationGuestModel, InvitationGuestID> {
    
    @Query("SELECT i FROM InvitationGuestModel i WHERE i.invitation.invitation_uuid = :invitation_uuid AND i.guest.name = :guestName")
    Optional<InvitationGuestModel> findByInvitationUUIDAndGuestName(@Param("invitation_uuid") UUID invitation_uuid, @Param("guestName") String guestName);
}
