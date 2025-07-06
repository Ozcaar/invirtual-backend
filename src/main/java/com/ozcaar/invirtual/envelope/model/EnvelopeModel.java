package com.ozcaar.invirtual.envelope.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "envelope")
public class EnvelopeModel {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer envelope_id;

    @Column(nullable = false)
    private String img_url;

    // Getters & setters

    public Integer getEnvelope_id() {
        return envelope_id;
    }

    public void setEnvelope_id(Integer envelope_id) {
        this.envelope_id = envelope_id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
