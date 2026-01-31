package com.ozcaar.invirtual.invitation.policy;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.invitation.model.InvitationModel;

public class DemoInvitationPolicy implements InvitationPolicy {

    @Override
    public void apply(InvitationModel inv) {
        inv.setIsDemo(true);
        inv.setMaxPeople(50);
        inv.setLimitDate(LocalDateTime.now().plusDays(3));
    }
}