package com.ozcaar.invirtual.common.model.manytomany;

import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.user.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_invitation")
public class UserInvitationModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_invitation_id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UserModel user_id;

    @OneToOne
    @JoinColumn(name = "invitation_id", unique = true, nullable = false)
    private InvitationModel invitation_id;

    // Getters & setters

    public Integer getUser_invitation_id() {
        return user_invitation_id;
    }

    public void setUser_invitation_id(Integer user_invitation_id) {
        this.user_invitation_id = user_invitation_id;
    }

    public UserModel getUser_id() {
        return user_id;
    }

    public void setUser_id(UserModel user_id) {
        this.user_id = user_id;
    }

    public InvitationModel getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(InvitationModel invitation_id) {
        this.invitation_id = invitation_id;
    }

}
