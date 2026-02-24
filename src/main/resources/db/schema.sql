
-- Unique constraints and indexes for the billing and entitlement system.
CREATE UNIQUE INDEX IF NOT EXISTS uniq_active_plan_price
ON plan_price (plan_id, environment, currency, billing_type)
WHERE active = true;

-- A Stripe session can only be associated with one entitlement, but an entitlement may not have a Stripe session (e.g. for free plans).
CREATE UNIQUE INDEX IF NOT EXISTS uniq_entitlement_stripe_session
ON entitlements (stripe_session_id);

-- A user can only have one unconsumed entitlement at a time, but can have multiple consumed entitlements.
CREATE UNIQUE INDEX IF NOT EXISTS ux_entitlement_user_unconsumed
ON entitlement (user_id)
WHERE consumed_at IS NULL;