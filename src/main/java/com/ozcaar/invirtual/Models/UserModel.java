package com.ozcaar.invirtual.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ozcaar.invirtual.Models.ManyToManyTables.UserRoleModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class UserModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = true)
    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password_hash;

    @Column(name = "last_login")
    private LocalDateTime last_login;

    @Column(nullable = false)
    private Boolean active;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserRoleModel> user_roles = new ArrayList<>();

    // Getters & setters

    public List<UserRoleModel> getUser_roles() {
        return user_roles;
    }

    public void setUser_roles(List<UserRoleModel> user_roles) {
        this.user_roles = user_roles;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
}
