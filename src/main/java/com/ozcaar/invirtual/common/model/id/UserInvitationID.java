package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.Objects;

public class UserInvitationID implements Serializable {

    private Integer user_id;
    private Integer invitation_id;

    // Constructor
    public UserInvitationID() {}

    // Constructor fields
    public UserInvitationID(Integer user_id, Integer invitation_id) {
        this.user_id = user_id;
        this.invitation_id = invitation_id;
    }

    // Getters & setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(Integer invitation_id) {
        this.invitation_id = invitation_id;
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInvitationID)) return false;

        UserInvitationID that = (UserInvitationID) o;
        return  Objects.equals(getUser_id(), that.getUser_id()) &&
                Objects.equals(getInvitation_id(), that.getInvitation_id());
    }

    // Hash code
    public int hashCode() {
        return Objects.hash(getUser_id(), getInvitation_id());
    }

}
