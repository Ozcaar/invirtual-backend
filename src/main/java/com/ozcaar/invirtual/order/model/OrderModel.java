package com.ozcaar.invirtual.order.model;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.user.model.UserModel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // @Column(nullable = false, length = 30)
    // private String status; // PENDING, PAID, CANCELED, REFUNDED...

    @Column(nullable = false)
    private Integer totalCents;

    @Column(nullable = false, length = 10)
    private String currency; // "MXN"

    @Column(length = 30)
    private String provider; // "stripe", "mercadopago", etc.

    @Column(name = "provider_ref", length = 100)
    private String providerRef; // id externo

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime paidAt;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private EntitlementModel entitlement;

    // Getters & setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Integer getTotalCents() {
        return totalCents;
    }

    public void setTotalCents(Integer totalCents) {
        this.totalCents = totalCents;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderRef() {
        return providerRef;
    }

    public void setProviderRef(String providerRef) {
        this.providerRef = providerRef;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(LocalDateTime paidAt) {
        this.paidAt = paidAt;
    }

    public EntitlementModel getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(EntitlementModel entitlement) {
        this.entitlement = entitlement;
    }
}
