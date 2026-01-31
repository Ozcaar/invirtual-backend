package com.ozcaar.invirtual.invitation.policy;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.invitation.model.InvitationModel;

public class EntitlementInvitationPolicy implements InvitationPolicy {

    private final EntitlementModel ent;

    public EntitlementInvitationPolicy(EntitlementModel ent) {
        this.ent = ent;
    }

    @Override
    public void apply(InvitationModel inv) {
        inv.setIsDemo(false);
        inv.setMaxPeople(ent.getMaxPeople());
        inv.setLimitDate(LocalDateTime.now().plusDays(ent.getDurationDays()));
    }
}