package com.ozcaar.invirtual.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.common.model.id.UserInvitationID;
import com.ozcaar.invirtual.common.model.manytomany.UserInvitationModel;

public interface UserInvitationRepository extends JpaRepository<UserInvitationModel, UserInvitationID>{

    @Query("SELECT u FROM UserInvitationModel u WHERE u.user.user_id = :user_id AND u.invitation.name = :invitationName")
    Optional<UserInvitationModel> findByUserIdAndInvitationName(@Param("user_id") Integer user_id, @Param("invitationName") String invitationName);

    // @Query("SELECT u FROM UserInvitationModel u WHERE u.user.user_id = :user_id AND u.invitation.is_demo = true")
    // Optional<UserInvitationModel> findDemoInvitationByUserAndName(
    //     @Param("user_id") Integer user_id,
    //     @Param("invitation_name") String invitation_name
    // );

    @Query("SELECT COUNT(u) > 0 FROM UserInvitationModel u WHERE u.user.user_id = :user_id AND u.invitation.is_demo = true")
    boolean existsDemoInvitation(@Param("user_id") Integer user_id);
}
