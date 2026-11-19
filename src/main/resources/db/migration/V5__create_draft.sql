CREATE TABLE draft (
    id uuid PRIMARY KEY,
    user_id integer NOT NULL REFERENCES app_user(user_id),
    plan_id uuid NOT NULL REFERENCES plan(id),
    invitation_type_id integer REFERENCES invitation_type(invitation_type_id),
    name varchar(100),
    slug_url varchar(50),
    schema_version integer NOT NULL DEFAULT 1 CHECK (schema_version > 0),
    configuration jsonb NOT NULL DEFAULT '{}'::jsonb CHECK (jsonb_typeof(configuration) = 'object'),
    version bigint NOT NULL DEFAULT 0,
    created_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_draft_user UNIQUE (user_id)
);
