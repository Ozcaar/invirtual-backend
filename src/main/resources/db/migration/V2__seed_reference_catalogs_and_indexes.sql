-- Catalog rows are stable reference data; natural-key conflict handling makes
-- deployment/replay safe and preserves existing IDs and relationships.
CREATE UNIQUE INDEX IF NOT EXISTS ux_role_name ON role (name);
CREATE UNIQUE INDEX IF NOT EXISTS ux_guest_group_name ON guest_group (name);

INSERT INTO role (name)
VALUES ('ROLE_ADMIN'), ('ROLE_DEV'), ('ROLE_USER'), ('ROLE_SUPPORT'), ('ROLE_COLABORATOR')
ON CONFLICT (name) DO NOTHING;

INSERT INTO invitation_type (name, description)
VALUES
    ('BODA', ''),
    ('CUMPLEAÑOS', ''),
    ('EMPRESARIAL', ''),
    ('INFANTIL', ''),
    ('OTRO', '')
ON CONFLICT (name) DO NOTHING;

INSERT INTO guest_group (name, description)
VALUES
    ('FAMILIA', 'Familia cercana'),
    ('AMIGOS', 'Amigos del anfitrión'),
    ('VIP', 'Invitados especiales'),
    ('OTRO', 'Otros invitados')
ON CONFLICT (name) DO NOTHING;

INSERT INTO invitation_type_guest_group (guest_group_id, invitation_type_id)
SELECT g.guest_group_id, i.invitation_type_id
FROM guest_group g
CROSS JOIN invitation_type i
WHERE (g.name IN ('FAMILIA', 'AMIGOS', 'OTRO') AND i.name IN ('BODA', 'CUMPLEAÑOS'))
   OR (g.name = 'VIP' AND i.name = 'BODA')
ON CONFLICT (guest_group_id, invitation_type_id) DO NOTHING;

CREATE UNIQUE INDEX IF NOT EXISTS uniq_active_plan_price
    ON plan_price (plan_id, environment, currency, billing_type)
    WHERE active = true;

-- stripe_session_id belongs to orders, not entitlements. orders already has a
-- unique constraint on that column (V1); the old schema.sql index targeted an
-- absent column/table combination and is intentionally not recreated.
CREATE UNIQUE INDEX IF NOT EXISTS ux_entitlement_user_unconsumed
    ON entitlements (user_id)
    WHERE consumed_at IS NULL;
