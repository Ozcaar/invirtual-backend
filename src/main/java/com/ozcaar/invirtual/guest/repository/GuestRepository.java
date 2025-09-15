package com.ozcaar.invirtual.guest.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.guest.model.GuestModel;

@Repository
public interface GuestRepository extends CrudRepository<GuestModel, Integer> {
    
    public abstract Optional<GuestModel> findByName(String name);
}
