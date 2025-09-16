package com.ozcaar.invirtual.guest.model;

import java.util.HashSet;
import java.util.Set;

import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "guest_group")
public class GuestGroupModel {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer guest_group_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    // @Column(nullable = false)
    // private String confirmation_message;
    
    // @Column(nullable = false)
    // private Boolean enabled_conf_mess;
    
    @ManyToMany
    @JoinTable(
        name = "invitation_type_guest_group",
        joinColumns = @JoinColumn(name = "guest_group_id"),
        inverseJoinColumns = @JoinColumn(name = "invitation_type_id")
        )
        private Set<InvitationTypeModel> invitationTypes = new HashSet<>();
        
    // Getters & setters
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<InvitationTypeModel> getInvitationTypes() {
        return invitationTypes;
    }
        
    public void setInvitationTypes(Set<InvitationTypeModel> invitationTypes) {
        this.invitationTypes = invitationTypes;
    }

    public Integer getGuest_group_id() {
        return guest_group_id;
    }

    public void setGuest_group_id(Integer guest_group_id) {
        this.guest_group_id = guest_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public String getConfirmation_message() {
    //     return confirmation_message;
    // }

    // public void setConfirmation_message(String confirmation_message) {
    //     this.confirmation_message = confirmation_message;
    // }

    // public Boolean getEnabled_conf_mess() {
    //     return enabled_conf_mess;
    // }

    // public void setEnabled_conf_mess(Boolean enabled_conf_mess) {
    //     this.enabled_conf_mess = enabled_conf_mess;
    // }

}
