package com.ozcaar.invirtual.invitation.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.invitation.model.InvitationModel;

@Repository
public interface InvitationRepository extends CrudRepository<InvitationModel, Integer> {
       public abstract Optional<InvitationModel> findByName(String name);

}
