package com.ozcaar.invirtual.invitation.model;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation")
public class InvitationModel {
    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false, columnDefinition = "uuid")
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID invitation_uuid;

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
    private Integer max_people;

    @Column(nullable = false)
    private String slug_url;

    private String confirmation_message;

    @Column(nullable = false)
    private LocalDateTime creation_date;

    @Column(nullable = false)
    private LocalDateTime limit_date;

    @Column(nullable = false)
    private Boolean is_demo;

    @Column(nullable = false)
    private Boolean active;

    // Getters & setters

    public InvitationTypeModel getInvitation_type_id() {
        return invitation_type_id;
    }

    public void setInvitation_type_id(InvitationTypeModel invitation_type_id) {
        this.invitation_type_id = invitation_type_id;
    }

    public UUID getInvitation_uuid() {
        return invitation_uuid;
    }

    public void setInvitation_uuid(UUID invitation_uuid) {
        this.invitation_uuid = invitation_uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getConfirmation_message() {
        return confirmation_message;
    }

    public void setConfirmation_message(String confirmation_message) {
        this.confirmation_message = confirmation_message;
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
