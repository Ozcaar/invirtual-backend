package com.ozcaar.invirtual.guest.dto.read;

import java.time.LocalDateTime;

public class GuestReadDTO {
    
    public Integer guest_id;
    // public GuestGroup guest_group_id;
    // public SignBook sign_book_id;
    public String name;
    public String email;
    public LocalDateTime confirmed_date;
    public Boolean confirmed;
    public Boolean link_opened;
    public Boolean enabled;

    // Getters & setters

    public Integer getGuest_id() {
        return guest_id;
    }
    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
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
