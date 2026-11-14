package com.ozcaar.invirtual.invitation.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;

@Repository
public interface InvitationTypeRepository extends CrudRepository<InvitationTypeModel, Integer> {
    List<InvitationTypeModel> findAllByActiveTrueOrderByNameAsc();

    Optional<InvitationTypeModel> findByName(String name);

    Optional<InvitationTypeModel> findByInvitationTypeId(Integer invitationTypeId);

}
