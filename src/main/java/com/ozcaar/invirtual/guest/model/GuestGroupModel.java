package com.ozcaar.invirtual.guest.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "guest_group")
public class GuestGroupModel {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invitated_group_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String confirmation_message;

    @Column(nullable = false)
    private Boolean enabled_conf_mess;

    // Getters & setters

    public Integer getInvitated_group_id() {
        return invitated_group_id;
    }

    public void setInvitated_group_id(Integer invitated_group_id) {
        this.invitated_group_id = invitated_group_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfirmation_message() {
        return confirmation_message;
    }

    public void setConfirmation_message(String confirmation_message) {
        this.confirmation_message = confirmation_message;
    }

    public Boolean getEnabled_conf_mess() {
        return enabled_conf_mess;
    }

    public void setEnabled_conf_mess(Boolean enabled_conf_mess) {
        this.enabled_conf_mess = enabled_conf_mess;
    }

}
