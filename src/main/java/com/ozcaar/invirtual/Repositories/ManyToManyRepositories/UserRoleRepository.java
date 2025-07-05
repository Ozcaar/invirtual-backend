package com.ozcaar.invirtual.Repositories.ManyToManyRepositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.Models.ManyToManyTables.UserRoleModel;
import com.ozcaar.invirtual.Models.ManyToManyTables.CompositeIDs.UserRoleID;

public interface UserRoleRepository extends JpaRepository<UserRoleModel, UserRoleID> {
    
}
