package com.ozcaar.invirtual.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.common.model.id.UserInvitationID;
import com.ozcaar.invirtual.common.model.manytomany.UserInvitationModel;

public interface UserInvitationRepository extends JpaRepository<UserInvitationModel, UserInvitationID>{

    @Query("SELECT u FROM UserInvitationModel u WHERE u.user.user_id = :userId AND u.invitation.name = :invitationName")
Optional<UserInvitationModel> findByUserIdAndInvitationName(@Param("userId") Integer userId, @Param("invitationName") String invitationName);
}
