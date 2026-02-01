package com.ozcaar.invirtual.invitation.policy;

import com.ozcaar.invirtual.entitlement.model.EntitlementModel;
import com.ozcaar.invirtual.invitation.model.InvitationTypeModel;

public record CreationContext(
                InvitationTypeModel invitationType,
                InvitationPolicy policy,
                EntitlementModel entitlement
) {
}
