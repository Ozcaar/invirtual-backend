package com.ozcaar.invirtual.common.model.manytomany;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.common.model.id.UserInvitationID;
import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.user.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_invitation")
public class UserInvitationModel {
    
    @EmbeddedId
    private UserInvitationID id = new UserInvitationID();

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @MapsId("invitationUuid")
    @JoinColumn(name = "invitation_uuid")
    private InvitationModel invitation;

    @Column(nullable = true)
    private LocalDateTime created_at;

    // Getters & setters

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public InvitationModel getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationModel invitation) {
        this.invitation = invitation;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
