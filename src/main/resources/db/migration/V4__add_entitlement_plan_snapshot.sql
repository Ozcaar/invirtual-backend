ALTER TABLE entitlements
    ADD COLUMN plan_id uuid REFERENCES plan(id),
    ADD COLUMN plan_code_snapshot varchar(40),
    ADD COLUMN plan_name_snapshot varchar(80);

-- Recover plan identity only when the stored price maps to one plan.
-- Historical names cannot be reconstructed: these labels come from today's catalog.
-- Existing max_people and duration_days snapshots must remain unchanged.
WITH price_plans AS (
    SELECT stripe_price_id, plan_id
    FROM plan_price
    UNION
    SELECT price_override.stripe_price_id_override, base_price.plan_id
    FROM plan_price_override price_override
    JOIN plan_price base_price ON base_price.id = price_override.plan_price_id
), recoverable_plans AS (
    SELECT mapping.stripe_price_id, mapping.plan_id
    FROM price_plans mapping
    WHERE NOT EXISTS (
        SELECT 1
        FROM price_plans other
        WHERE other.stripe_price_id = mapping.stripe_price_id
          AND other.plan_id <> mapping.plan_id
    )
)
UPDATE entitlements entitlement
SET plan_id = recovered_plan.id,
    plan_code_snapshot = recovered_plan.code,
    plan_name_snapshot = recovered_plan.name
FROM orders purchase
JOIN recoverable_plans mapping ON mapping.stripe_price_id = purchase.stripe_price_id
JOIN plan recovered_plan ON recovered_plan.id = mapping.plan_id
WHERE entitlement.order_id = purchase.id;
