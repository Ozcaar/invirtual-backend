package com.ozcaar.invirtual.invitation.policy;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.invitation.model.InvitationModel;

public class EntitlementInvitationPolicy implements InvitationPolicy {

    private final EntitlementModel ent;
    private final LocalDateTime now;

    public EntitlementInvitationPolicy(EntitlementModel ent, LocalDateTime now) {
        this.ent = ent;
        this.now = now;
    }

    @Override
    public void apply(InvitationModel inv) {
        inv.setIsDemo(false);
        inv.setMaxPeople(ent.getMaxPeople());
        inv.setLimitDate(now.plusDays(ent.getDurationDays()));
    }
}