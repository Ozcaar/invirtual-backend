package com.ozcaar.invirtual.invitation.policy;

import java.time.LocalDateTime;

import com.ozcaar.invirtual.invitation.model.InvitationModel;

public class DemoInvitationPolicy implements InvitationPolicy {

    public static final int MAX_PEOPLE = 50;
    public static final int DURATION_DAYS = 3;

    @Override
    public void apply(InvitationModel inv) {
        inv.setIsDemo(true);
        inv.setMaxPeople(MAX_PEOPLE);
        inv.setLimitDate(LocalDateTime.now().plusDays(DURATION_DAYS));
    }
}
