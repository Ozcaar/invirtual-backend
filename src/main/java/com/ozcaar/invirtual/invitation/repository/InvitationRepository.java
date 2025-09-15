package com.ozcaar.invirtual.invitation.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.invitation.model.InvitationModel;

@Repository
public interface InvitationRepository extends CrudRepository<InvitationModel, Integer> {
       public abstract Optional<InvitationModel> findByName(String name);
       
       @Query("SELECT i FROM InvitationModel i WHERE i.invitation_uuid = :uuid")
       Optional<InvitationModel> findByUUID(@Param("uuid") UUID uuid);

}
