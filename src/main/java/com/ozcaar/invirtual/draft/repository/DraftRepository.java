package com.ozcaar.invirtual.draft.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozcaar.invirtual.draft.model.DraftModel;

public interface DraftRepository extends JpaRepository<DraftModel, UUID> {
    Optional<DraftModel> findByUser_UserId(Integer userId);
}
