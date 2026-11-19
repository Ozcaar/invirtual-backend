package com.ozcaar.invirtual.entitlement.model;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.invitation.model.InvitationModel;
import com.ozcaar.invirtual.order.model.OrderModel;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.user.model.UserModel;

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
@Table(name = "entitlements")
public class EntitlementModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // dueño del entitlement
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    // de qué compra salió
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", unique = true)
    private OrderModel order; // nullable si es demo/grant manual/admin

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PlanModel plan;

    @Column(name = "plan_code_snapshot", length = 40)
    private String planCodeSnapshot;

    @Column(name = "plan_name_snapshot", length = 80)
    private String planNameSnapshot;

    // snapshot de capacidades (evita que cambien si cambias planes después)
    @Column(nullable = false)
    private Integer maxPeople;

    @Column(nullable = false)
    private Integer durationDays;

    // consumo
    private LocalDateTime consumedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_uuid", unique = true)
    private InvitationModel invitation; // nullable hasta que se consuma

    public boolean isAvailable(LocalDateTime now) {
        return consumedAt == null;
    }

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

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public PlanModel getPlan() {
        return plan;
    }

    public void setPlan(PlanModel plan) {
        this.plan = plan;
    }

    public String getPlanCodeSnapshot() {
        return planCodeSnapshot;
    }

    public void setPlanCodeSnapshot(String planCodeSnapshot) {
        this.planCodeSnapshot = planCodeSnapshot;
    }

    public String getPlanNameSnapshot() {
        return planNameSnapshot;
    }

    public void setPlanNameSnapshot(String planNameSnapshot) {
        this.planNameSnapshot = planNameSnapshot;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    public LocalDateTime getConsumedAt() {
        return consumedAt;
    }

    public void setConsumedAt(LocalDateTime consumedAt) {
        this.consumedAt = consumedAt;
    }

    public InvitationModel getInvitation() {
        return invitation;
    }

    public void setInvitation(InvitationModel invitation) {
        this.invitation = invitation;
    }
}
