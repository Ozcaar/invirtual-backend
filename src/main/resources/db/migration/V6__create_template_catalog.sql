-- Revision content and compatibility associations are immutable in the application.
-- New designs must use a new revision/id; only active and position may change.
CREATE TABLE template (
    id uuid PRIMARY KEY,
    code varchar(80) NOT NULL,
    revision integer NOT NULL DEFAULT 1 CHECK (revision > 0),
    name varchar(120) NOT NULL,
    description text,
    thumbnail_url varchar(2048),
    initial_configuration jsonb NOT NULL DEFAULT '{}'::jsonb CHECK (jsonb_typeof(initial_configuration) = 'object'),
    schema_version integer NOT NULL DEFAULT 1 CHECK (schema_version > 0),
    active boolean NOT NULL DEFAULT true,
    position integer NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_template_revision UNIQUE (code, revision)
);

CREATE UNIQUE INDEX uq_template_active_code ON template (code) WHERE active = true;

CREATE TABLE template_plan (
    template_id uuid NOT NULL REFERENCES template(id),
    plan_id uuid NOT NULL REFERENCES plan(id),
    PRIMARY KEY (template_id, plan_id)
);

CREATE TABLE template_invitation_type (
    template_id uuid NOT NULL REFERENCES template(id),
    invitation_type_id integer NOT NULL REFERENCES invitation_type(invitation_type_id),
    PRIMARY KEY (template_id, invitation_type_id)
);

ALTER TABLE draft ADD COLUMN template_id uuid REFERENCES template(id);
