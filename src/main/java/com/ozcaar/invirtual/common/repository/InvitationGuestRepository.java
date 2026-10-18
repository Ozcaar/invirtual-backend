package com.ozcaar.invirtual.common.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.common.model.id.InvitationGuestID;
import com.ozcaar.invirtual.common.model.manytomany.InvitationGuestModel;
import com.ozcaar.invirtual.guest.model.GuestModel;

public interface InvitationGuestRepository extends JpaRepository<InvitationGuestModel, InvitationGuestID> {

    @Query("SELECT ig FROM InvitationGuestModel ig WHERE ig.invitation.invitationUuid = :invitationUuid AND ig.guest.guest_id = :guestId")
    Optional<InvitationGuestModel> findByInvitationUuidAndGuestId(@Param("invitationUuid") UUID invitationUuid,
            @Param("guestId") Integer guestId);

    @Query("SELECT COUNT(ig) FROM InvitationGuestModel ig WHERE ig.guest.guest_id = :guestId")
    long countAssignmentsByGuestId(@Param("guestId") Integer guestId);

    @Query("SELECT i FROM InvitationGuestModel i WHERE i.invitation.invitationUuid = :invitation_uuid AND i.guest.name = :guestName")
    Optional<InvitationGuestModel> findByInvitationUUIDAndGuestName(@Param("invitation_uuid") UUID invitation_uuid,
            @Param("guestName") String guestName);

    @Query("SELECT i.guest FROM InvitationGuestModel i WHERE i.invitation.invitationUuid = :invitation_uuid ORDER BY i.guest.name ASC")
    List<GuestModel> findGuestsByInvitationUuid(@Param("invitation_uuid") UUID invitation_uuid);
}
