package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;

public class InvitationGuestID implements Serializable {
 
    @Column(name = "invitation_uuid")
    private UUID invitationUuid;

    @Column(name = "guest_id")
    private Integer guest_id;

    @Column(name = "guest_group_id")
    private Integer guest_group_id;
    
    // Constructor
    public InvitationGuestID() {}

    // Constructor fields
    public InvitationGuestID(UUID invitationUuid, Integer guest_id, Integer guest_group_id) {
        this.invitationUuid = invitationUuid;
        this.guest_id = guest_id;
        this.guest_group_id = guest_group_id;
    }

    // Getters & setters

    public UUID getInvitationUuid() {
        return invitationUuid;
    }

    public void setInvitationUuid(UUID invitationUuid) {
        this.invitationUuid = invitationUuid;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public Integer getGuest_group_id() {
        return guest_group_id;
    }

    public void setGuest_group_id(Integer guest_group_id) {
        this.guest_group_id = guest_group_id;
    }

    // Hash code
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((invitationUuid == null) ? 0 : invitationUuid.hashCode());
        result = prime * result + ((guest_id == null) ? 0 : guest_id.hashCode());
        result = prime * result + ((guest_group_id == null) ? 0 : guest_group_id.hashCode());
        return result;
    }

    // Equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InvitationGuestID other = (InvitationGuestID) obj;
        if (invitationUuid == null) {
            if (other.invitationUuid != null)
                return false;
        } else if (!invitationUuid.equals(other.invitationUuid))
            return false;
        if (guest_id == null) {
            if (other.guest_id != null)
                return false;
        } else if (!guest_id.equals(other.guest_id))
            return false;
        if (guest_group_id == null) {
            if (other.guest_group_id != null)
                return false;
        } else if (!guest_group_id.equals(other.guest_group_id))
            return false;
        return true;
    }
}
