package com.ozcaar.invirtual.draft.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.plan.model.PlanModel;
import com.ozcaar.invirtual.user.model.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "draft", uniqueConstraints = @UniqueConstraint(name = "uq_draft_user", columnNames = "user_id"))
@Getter
@Setter
public class DraftModel {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "plan_id", nullable = false)
    private PlanModel plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_type_id")
    private InvitationTypeModel invitationType;

    @Column(length = 100)
    private String name;

    @Column(name = "slug_url", length = 50)
    private String slugUrl;

    @Column(name = "schema_version", nullable = false)
    private Integer schemaVersion;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private JsonNode configuration;

    @Version
    @Column(nullable = false)
    private Long version;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
