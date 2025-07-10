package com.ozcaar.invirtual.common.model.manytomany;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.common.model.id.UserRoleID;
import com.ozcaar.invirtual.role.model.RoleModel;
import com.ozcaar.invirtual.user.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRoleModel {
    
    @EmbeddedId
    private UserRoleID id = new UserRoleID();

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @MapsId("role_id")
    @JoinColumn(name = "role_id")
    private RoleModel role;

    @Column(nullable = true)
    private LocalDateTime assign_date;

    @Column(nullable = false)
    private Boolean active;

    // If any other field is needed, just add here above...

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getAssign_date() {
        return assign_date;
    }

    public void setAssign_date(LocalDateTime assign_date) {
        this.assign_date = assign_date;
    }

    public UserRoleID getId() {
        return id;
    }

    public void setId(UserRoleID id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }
}
