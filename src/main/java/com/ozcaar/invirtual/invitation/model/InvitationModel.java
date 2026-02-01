package com.ozcaar.invirtual.invitation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation")
public class InvitationModel {
    @Id
    @GeneratedValue
    @Column(name = "invitation_uuid", unique = true, nullable = false, columnDefinition = "uuid")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID invitationUuid;

    // @OneToOne
    // @JoinColumn(name = "envelope_id", unique = true, nullable = false)
    // private EnvelopeModel envelope_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_type_id", nullable = false)
    private InvitationTypeModel invitation_type_id;

    // private MusicModel music_id;

    // private SignBookModel sign_book_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer maxPeople;

    @Column(name = "slug_url", nullable = false)
    private String slugUrl;

    private String confirmationMessage;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "limit_date", nullable = false)
    private LocalDateTime limitDate;

    @Column(name = "is_demo", nullable = false)
    private Boolean isDemo;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @OneToOne(mappedBy = "invitation", fetch = FetchType.LAZY)
    private EntitlementModel entitlement;

    // Getters & setters

    public EntitlementModel getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(EntitlementModel entitlement) {
        this.entitlement = entitlement;
    }

    public InvitationTypeModel getInvitation_type_id() {
        return invitation_type_id;
    }

    public void setInvitation_type_id(InvitationTypeModel invitation_type_id) {
        this.invitation_type_id = invitation_type_id;
    }

    public UUID getInvitationUuid() {
        return invitationUuid;
    }

    public void setInvitationUuid(UUID invitationUuid) {
        this.invitationUuid = invitationUuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
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
