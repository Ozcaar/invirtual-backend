package com.ozcaar.invirtual.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.common.model.id.UserRoleID;
import com.ozcaar.invirtual.common.model.manytomany.UserRoleModel;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleID> {
    
}
