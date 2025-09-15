package com.ozcaar.invirtual.invitation.dto.read;

import java.time.LocalDateTime;
import java.util.UUID;

public class InvitationReadDTO {
    
    private UUID invitation_uuid;
    // private Integer envelope_id;
    // private Integer invitation_type_id;
    // private Integer music_id;
    // private Integer sign_book_id;
    private Integer max_people;
    private String slug_url;
    private LocalDateTime creation_date;
    private LocalDateTime limit_date;
    private Boolean is_demo;
    private Boolean active;

    // Getters & setters

    public UUID getInvitation_uuid() {
        return invitation_uuid;
    }
    public void setInvitation_uuid(UUID invitation_uuid) {
        this.invitation_uuid = invitation_uuid;
    }
    public Integer getMax_people() {
        return max_people;
    }
    public void setMax_people(Integer max_people) {
        this.max_people = max_people;
    }
    public String getSlug_url() {
        return slug_url;
    }
    public void setSlug_url(String slug_url) {
        this.slug_url = slug_url;
    }
    public LocalDateTime getCreation_date() {
        return creation_date;
    }
    public void setCreation_date(LocalDateTime creation_date) {
        this.creation_date = creation_date;
    }
    public LocalDateTime getLimit_date() {
        return limit_date;
    }
    public void setLimit_date(LocalDateTime limit_date) {
        this.limit_date = limit_date;
    }
    public Boolean getIs_demo() {
        return is_demo;
    }
    public void setIs_demo(Boolean is_demo) {
        this.is_demo = is_demo;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
