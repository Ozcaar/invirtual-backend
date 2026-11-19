package com.ozcaar.invirtual.template.model;

import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.fasterxml.jackson.databind.JsonNode;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;
import com.ozcaar.invirtual.plan.model.PlanModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "template", uniqueConstraints = @UniqueConstraint(name = "uq_template_revision", columnNames = { "code", "revision" }))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TemplateModel {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, length = 80, updatable = false)
    private String code;

    @Column(nullable = false, updatable = false)
    private int revision;

    @Column(nullable = false, length = 120, updatable = false)
    private String name;

    @Column(columnDefinition = "text", updatable = false)
    private String description;

    @Column(name = "thumbnail_url", length = 2048, updatable = false)
    private String thumbnailUrl;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "initial_configuration", nullable = false, columnDefinition = "jsonb", updatable = false)
    private JsonNode initialConfiguration;

    @Column(name = "schema_version", nullable = false, updatable = false)
    private int schemaVersion;

    @Setter
    @Column(nullable = false)
    private boolean active;

    @Setter
    @Column(nullable = false)
    private int position;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Immutable
    @ManyToMany
    @JoinTable(name = "template_plan", joinColumns = @JoinColumn(name = "template_id"), inverseJoinColumns = @JoinColumn(name = "plan_id"))
    private Set<PlanModel> plans = new HashSet<>();

    @Immutable
    @ManyToMany
    @JoinTable(name = "template_invitation_type", joinColumns = @JoinColumn(name = "template_id"), inverseJoinColumns = @JoinColumn(name = "invitation_type_id"))
    private Set<InvitationTypeModel> invitationTypes = new HashSet<>();

    public JsonNode getInitialConfiguration() {
        return initialConfiguration == null ? null : initialConfiguration.deepCopy();
    }

    public Set<PlanModel> getPlans() {
        return Collections.unmodifiableSet(plans);
    }

    public Set<InvitationTypeModel> getInvitationTypes() {
        return Collections.unmodifiableSet(invitationTypes);
    }
}
