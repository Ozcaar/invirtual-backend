package com.ozcaar.invirtual.invitation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InvitationTypeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invitation_type_id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    // Getters & setters

    public Integer getInvitation_type_id() {
        return invitation_type_id;
    }

    public void setInvitation_type_id(Integer invitation_type_id) {
        this.invitation_type_id = invitation_type_id;
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
