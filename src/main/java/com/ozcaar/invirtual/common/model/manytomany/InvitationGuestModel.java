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
    @MapsId("invitation_uuid")
    @JoinColumn(name = "invitation_uuid")
    private InvitationModel invitation;

    @ManyToOne
    @MapsId("guest_id")
    @JoinColumn(name = "guest_id")
    private GuestModel guest;

    @ManyToOne
    @MapsId("invitated_group_id")
    @JoinColumn(name = "invitated_group_id")
    private GuestGroupModel invitated_group;

    // Getters & setters

    public GuestGroupModel getInvitated_group() {
        return invitated_group;
    }

    public void setInvitated_group(GuestGroupModel invitated_group) {
        this.invitated_group = invitated_group;
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
