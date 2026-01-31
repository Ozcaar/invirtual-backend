package com.ozcaar.invirtual.entitlement.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;

import jakarta.persistence.LockModeType;

public interface EntitlementRepository extends JpaRepository<EntitlementModel, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
          select e from EntitlementModel e
          where e.user.supabaseId = :supabaseId
            and e.invitationType = :type
            and e.consumedAt is null
            and (e.expiresAt is null or e.expiresAt > :now)
          order by e.id asc
      """)
  Optional<EntitlementModel> findFirstAvailableForUpdate(
      @Param("supabaseId") String supabaseId,
      @Param("type") InvitationTypeModel type,
      @Param("now") LocalDateTime now);
}
