package com.ozcaar.invirtual.invitation.policy;

import com.ozcaar.invirtual.invitation.model.InvitationModel;

public interface InvitationPolicy {
    void apply(InvitationModel invitation);
}
