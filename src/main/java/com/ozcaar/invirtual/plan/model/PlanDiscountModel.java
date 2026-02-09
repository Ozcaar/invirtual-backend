package com.ozcaar.invirtual.plan.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan_discount")
public class PlanDiscountModel {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_price_id", nullable = false)
    private PlanPriceModel planPrice;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    // Optional code to apply the discount. If null, it's an auto-applied discount
    // (no code needed, applied based on priority and conditions)
    @Column(name = "public_code", length = 60)
    private String publicCode;

    @Column(name = "stripe_coupon_id", length = 120)
    private String stripeCouponId;

    @Column(name = "stripe_promotion_code_id", length = 120)
    private String stripePromotionCodeId;

    // Informative fields (optional cache). Stripe is still the source of truth.
    // @Enumerated(EnumType.STRING)
    // @Column(name = "discount_type", length = 10)
    // private DiscountTypeEnum discountType;

    // @Column(name = "percent_off")
    // private Integer percentOff;

    // @Column(name = "amount_off")
    // private Integer amountOff; // in cents

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @Column(name = "max_redemptions")
    private Integer maxRedemptions;

    @Column(name = "new_customers_only", nullable = false)
    private boolean newCustomersOnly = false;

    @Column(name = "priority", nullable = false)
    private int priority = 0;

    @Column(name = "stackable", nullable = false)
    private boolean stackable = false;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    // Getters & setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PlanPriceModel getPlanPrice() {
        return planPrice;
    }

    public void setPlanPrice(PlanPriceModel planPrice) {
        this.planPrice = planPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicCode() {
        return publicCode;
    }

    public void setPublicCode(String publicCode) {
        this.publicCode = publicCode;
    }

    public String getStripeCouponId() {
        return stripeCouponId;
    }

    public void setStripeCouponId(String stripeCouponId) {
        this.stripeCouponId = stripeCouponId;
    }

    public String getStripePromotionCodeId() {
        return stripePromotionCodeId;
    }

    public void setStripePromotionCodeId(String stripePromotionCodeId) {
        this.stripePromotionCodeId = stripePromotionCodeId;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public Integer getMaxRedemptions() {
        return maxRedemptions;
    }

    public void setMaxRedemptions(Integer maxRedemptions) {
        this.maxRedemptions = maxRedemptions;
    }

    public boolean isNewCustomersOnly() {
        return newCustomersOnly;
    }

    public void setNewCustomersOnly(boolean newCustomersOnly) {
        this.newCustomersOnly = newCustomersOnly;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isStackable() {
        return stackable;
    }

    public void setStackable(boolean stackable) {
        this.stackable = stackable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
