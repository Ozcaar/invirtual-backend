package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class UserInvitationID implements Serializable {

    private Integer user_id;
    private UUID invitationUuid;

    // Constructor
    public UserInvitationID() {}

    // Constructor fields
    public UserInvitationID(Integer user_id, UUID invitationUuid) {
        this.user_id = user_id;
        this.invitationUuid = invitationUuid;
    }

    // Getters & setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public UUID getInvitationUuid() {
        return invitationUuid;
    }

    public void setInvitationUuid(UUID invitationUuid) {
        this.invitationUuid = invitationUuid;
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInvitationID)) return false;

        UserInvitationID that = (UserInvitationID) o;
        return  Objects.equals(getUser_id(), that.getUser_id()) &&
                Objects.equals(getInvitationUuid(), that.getInvitationUuid());
    }

    // Hash code
    public int hashCode() {
        return Objects.hash(getUser_id(), getInvitationUuid());
    }

}
