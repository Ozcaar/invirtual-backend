package com.ozcaar.invirtual.invitation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation_type")
public class InvitationTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invitationTypeId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    // Getters & setters

    public Integer getInvitationTypeId() {
        return invitationTypeId;
    }

    public void setInvitationTypeId(Integer invitationTypeId) {
        this.invitationTypeId = invitationTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
