package com.ozcaar.invirtual.invitation.dto.read;

import java.time.LocalDateTime;
import java.util.UUID;

public class InvitationReadDTO {

    private UUID invitationUuid;
    // private Integer envelope_id;
    // private Integer invitationTypeId;
    // private Integer music_id;
    // private Integer sign_book_id;
    private Integer maxPeople;
    private String slugUrl;
    private LocalDateTime creationDate;
    private LocalDateTime limitDate;
    private Boolean isDemo;
    private Boolean active;

    // Getters & setters

    public UUID getInvitationUuid() {
        return invitationUuid;
    }

    public void setInvitationUuid(UUID invitationUuid) {
        this.invitationUuid = invitationUuid;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getSlugUrl() {
        return slugUrl;
    }

    public void setSlugUrl(String slugUrl) {
        this.slugUrl = slugUrl;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(LocalDateTime limitDate) {
        this.limitDate = limitDate;
    }

    public Boolean getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Boolean isDemo) {
        this.isDemo = isDemo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
