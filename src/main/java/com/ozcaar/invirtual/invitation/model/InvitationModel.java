package com.ozcaar.invirtual.invitation.model;

import java.sql.Date;

import com.ozcaar.invirtual.envelope.model.EnvelopeModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "invitation")
public class InvitationModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer invitation_id;

    @OneToOne
    @JoinColumn(name = "envelope_id", unique = true, nullable = false)
    private EnvelopeModel envelope_id;

    // private InvitationTypeModel invitation_type_id;

    // private MusicModel music_id;

    // private SignBookModel sign_book_id;

    @Column(nullable = false)
    private Integer max_people;

    @Column(nullable = false)
    private String slug_url;

    @Column(nullable = false)
    private Date creation_date;

    @Column(nullable = false)
    private Date limit_date;

    @Column(nullable = false)
    private Boolean is_demo;

    @Column(nullable = false)
    private Boolean active;

    // Getters & setters

    public Integer getInvitation_id() {
        return invitation_id;
    }

    public void setInvitation_id(Integer invitation_id) {
        this.invitation_id = invitation_id;
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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getLimit_date() {
        return limit_date;
    }

    public void setLimit_date(Date limit_date) {
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
