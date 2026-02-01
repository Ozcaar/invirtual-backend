package com.ozcaar.invirtual.common.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ozcaar.invirtual.common.model.id.UserInvitationID;
import com.ozcaar.invirtual.common.model.manytomany.UserInvitationModel;

public interface UserInvitationRepository extends JpaRepository<UserInvitationModel, UserInvitationID> {

        // @Query("""
        // select ui from UserInvitationModel ui
        // where ui.user.userId = :userId
        // and ui.invitation.name = :name
        // """)
        // Optional<UserInvitationModel> findByUserIdAndInvitationName(
        // @Param("userId") Integer userId,
        // @Param("name") String name);

        Optional<UserInvitationModel> findByUserSupabaseIdAndInvitationName(String supabaseId, String name);

        // @Query("SELECT u FROM UserInvitationModel u WHERE u.user.user_id = :user_id
        // AND u.invitation.is_demo = true")
        // Optional<UserInvitationModel> findDemoInvitationByUserAndName(
        // @Param("user_id") Integer user_id,
        // @Param("invitation_name") String invitation_name
        // );

        @Query("""
                          select (count(ui) > 0) from UserInvitationModel ui
                          where ui.user.supabaseId = :supabaseId
                            and ui.invitation.isDemo = true
                            and ui.invitation.active = true
                            and ui.invitation.limitDate > :now
                        """)
        boolean existsActiveDemoInvitation(
                        @Param("supabaseId") String supabaseId,
                        @Param("now") LocalDateTime now);

        @Query("""
                          select (count(ui) > 0) from UserInvitationModel ui
                          where ui.user.supabaseId = :supabaseId
                            and ui.invitation.slugUrl = :slugUrl
                        """)
        boolean existsByUserSupabaseIdAndInvitationSlugUrl(
                        @Param("supabaseId") String supabaseId,
                        @Param("slugUrl") String slugUrl);

}
