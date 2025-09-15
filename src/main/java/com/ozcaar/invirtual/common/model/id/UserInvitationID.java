package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserInvitationID implements Serializable {

    private Integer user_id;
    private UUID invitation_uuid;

    // Constructor
    public UserInvitationID() {}

    // Constructor fields
    public UserInvitationID(Integer user_id, UUID invitation_uuid) {
        this.user_id = user_id;
        this.invitation_uuid = invitation_uuid;
    }

    // Getters & setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public UUID getInvitation_uuid() {
        return invitation_uuid;
    }

    public void setInvitation_uuid(UUID invitation_uuid) {
        this.invitation_uuid = invitation_uuid;
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInvitationID)) return false;

        UserInvitationID that = (UserInvitationID) o;
        return  Objects.equals(getUser_id(), that.getUser_id()) &&
                Objects.equals(getInvitation_uuid(), that.getInvitation_uuid());
    }

    // Hash code
    public int hashCode() {
        return Objects.hash(getUser_id(), getInvitation_uuid());
    }

}
