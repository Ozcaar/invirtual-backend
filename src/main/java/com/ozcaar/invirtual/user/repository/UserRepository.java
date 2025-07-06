package com.ozcaar.invirtual.user.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.user.model.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {

    public abstract Optional<UserModel> findByEmail(String user);
}
