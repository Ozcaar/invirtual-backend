CREATE UNIQUE INDEX IF NOT EXISTS uniq_active_plan_price
ON plan_price (plan_id, environment, currency, billing_type)
WHERE active = true;