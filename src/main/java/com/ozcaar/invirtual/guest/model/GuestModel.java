package com.ozcaar.invirtual.guest.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "guest")
public class GuestModel {
    
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer guest_id;

    // @Column(nullable = true)
    // public Integer invitated_group_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_group_id")
    private GuestGroupModel guest_group;

    // @Column(nullable = true)
    // public Integer sign_book_id;

    @Column(nullable = false)
    public String name;

    @Column(nullable = true)
    public String email;

    public LocalDateTime confirmed_date;
    public Boolean confirmed;
    public Boolean link_opened;

    @Column(nullable = false)
    public Boolean enabled;

    // Getters & setters
    
    public Integer getGuest_id() {
        return guest_id;
    }
    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }
    // public Integer getInvitated_group_id() {
    //     return invitated_group_id;
    // }
    // public void setInvitated_group_id(Integer invitated_group_id) {
    //     this.invitated_group_id = invitated_group_id;
    // }
    // public Integer getSign_book_id() {
    //     return sign_book_id;
    // }
    // public void setSign_book_id(Integer sign_book_id) {
    //     this.sign_book_id = sign_book_id;
    // }
    
    public GuestGroupModel getGuest_group() {
        return guest_group;
    }
    public void setGuest_group(GuestGroupModel guest_group) {
        this.guest_group = guest_group;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDateTime getConfirmed_date() {
        return confirmed_date;
    }
    public void setConfirmed_date(LocalDateTime confirmed_date) {
        this.confirmed_date = confirmed_date;
    }
    public Boolean getConfirmed() {
        return confirmed;
    }
    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }
    public Boolean getLink_opened() {
        return link_opened;
    }
    public void setLink_opened(Boolean link_opened) {
        this.link_opened = link_opened;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
