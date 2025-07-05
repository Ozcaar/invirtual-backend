package com.ozcaar.invirtual.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ozcaar.invirtual.Models.UserModel;

@Repository
public interface UserRepository extends CrudRepository<UserModel, Integer> {

    public abstract Optional<UserModel> findByEmail(String user);
}
