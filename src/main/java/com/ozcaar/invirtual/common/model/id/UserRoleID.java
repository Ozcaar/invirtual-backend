package com.ozcaar.invirtual.common.model.id;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;

public class UserRoleID implements Serializable {
    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "role_id")
    private Integer role_id;

    // Constructor
    public UserRoleID() {}

    // Constructor fields
    public UserRoleID(Integer user_id, Integer role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    // Getters & Setters
    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    // Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleID)) return false;

        UserRoleID that = (UserRoleID) o;
        return  Objects.equals(getUser_id(), that.getUser_id()) &&
                Objects.equals(getRole_id(), that.getRole_id());
    }

    // Hash code
    public int hashCode() {
        return Objects.hash(getUser_id(), getRole_id());
    }
}
