package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;

public class InvitationGuestID implements Serializable {
 
    @Column(name = "invitation_uuid")
    private UUID invitation_uuid;

    @Column(name = "guest_id")
    private Integer guest_id;

    @Column(name = "invitated_group_id")
    private Integer invitated_group_id;
    
    // Constructor
    public InvitationGuestID() {}

    // Constructor fields
    public InvitationGuestID(UUID invitation_uuid, Integer guest_id, Integer invitated_group_id) {
        this.invitation_uuid = invitation_uuid;
        this.guest_id = guest_id;
        this.invitated_group_id = invitated_group_id;
    }

    // Getters & setters

    public UUID getInvitation_uuid() {
        return invitation_uuid;
    }

    public void setInvitation_uuid(UUID invitation_uuid) {
        this.invitation_uuid = invitation_uuid;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public Integer getInvitated_group_id() {
        return invitated_group_id;
    }

    public void setInvitated_group_id(Integer invitated_group_id) {
        this.invitated_group_id = invitated_group_id;
    }

    // Hash code
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((invitation_uuid == null) ? 0 : invitation_uuid.hashCode());
        result = prime * result + ((guest_id == null) ? 0 : guest_id.hashCode());
        result = prime * result + ((invitated_group_id == null) ? 0 : invitated_group_id.hashCode());
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
        if (invitation_uuid == null) {
            if (other.invitation_uuid != null)
                return false;
        } else if (!invitation_uuid.equals(other.invitation_uuid))
            return false;
        if (guest_id == null) {
            if (other.guest_id != null)
                return false;
        } else if (!guest_id.equals(other.guest_id))
            return false;
        if (invitated_group_id == null) {
            if (other.invitated_group_id != null)
                return false;
        } else if (!invitated_group_id.equals(other.invitated_group_id))
            return false;
        return true;
    }
}
