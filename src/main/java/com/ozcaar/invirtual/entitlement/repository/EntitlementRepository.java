package com.ozcaar.invirtual.entitlement.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;

import jakarta.persistence.LockModeType;

public interface EntitlementRepository extends JpaRepository<EntitlementModel, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("""
          select e from EntitlementModel e
          join e.user u
          where u.supabaseId = :supabaseId
            and e.consumedAt is null
          order by e.id asc
      """)
  List<EntitlementModel> findAvailableForUpdate(
      @Param("supabaseId") String supabaseId,
      Pageable pageable);

  Optional<EntitlementModel> findByOrder_Id(UUID orderId);
}
