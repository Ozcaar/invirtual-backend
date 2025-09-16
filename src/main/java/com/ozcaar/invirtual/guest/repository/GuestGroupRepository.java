package com.ozcaar.invirtual.guest.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.guest.model.GuestGroupModel;

@Repository
public interface GuestGroupRepository extends CrudRepository<GuestGroupModel, Integer> {

    public abstract Optional<GuestGroupModel> findByName(String name);
}
