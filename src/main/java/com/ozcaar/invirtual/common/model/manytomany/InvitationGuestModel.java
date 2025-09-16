package com.ozcaar.invirtual.common.model.manytomany;

import com.ozcaar.invirtual.common.model.id.InvitationGuestID;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.guest.model.GuestGroupModel;
import com.ozcaar.invirtual.guest.model.GuestModel;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation_guest")
public class InvitationGuestModel {
    
    @EmbeddedId
    private InvitationGuestID id = new InvitationGuestID();

    @ManyToOne
    @MapsId("invitationUuid")
    @JoinColumn(name = "invitation_uuid")
    private InvitationModel invitation;

    @ManyToOne
    @MapsId("guest_id")
    @JoinColumn(name = "guest_id")
    private GuestModel guest;

    @ManyToOne
    @MapsId("guest_group_id")
    @JoinColumn(name = "guest_group_id")
    private GuestGroupModel guest_group;

    // Getters & setters

    public GuestGroupModel getGuest_group() {
        return guest_group;
    }

    public void setGuest_group(GuestGroupModel guest_group) {
        this.guest_group = guest_group;
    }

    public InvitationGuestID getId() {
        return id;
    }

    public void setId(InvitationGuestID id) {
        this.id = id;
    }

    public InvitationModel getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationModel invitation) {
        this.invitation = invitation;
    }

    public GuestModel getGuest() {
        return guest;
    }

    public void setGuest(GuestModel guest) {
        this.guest = guest;
    }
}
