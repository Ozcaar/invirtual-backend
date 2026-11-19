package com.ozcaar.invirtual.template.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.template.model.TemplateModel;

public interface TemplateRepository extends JpaRepository<TemplateModel, UUID> {

    @Query("""
            select distinct t from TemplateModel t
            join t.plans p
            join t.invitationTypes it
            where t.active = true and p.id = :planId
              and (:invitationTypeId is null or it.invitationTypeId = :invitationTypeId)
            order by t.position asc, t.name asc, t.code asc
            """)
    List<TemplateModel> findAvailable(@Param("planId") UUID planId,
            @Param("invitationTypeId") Integer invitationTypeId);
}
