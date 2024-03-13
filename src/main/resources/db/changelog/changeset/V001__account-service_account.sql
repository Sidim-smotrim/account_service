CREATE TABLE owner (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    owner_id BIGINT NOT NULL,
    owner_type VARCHAR(30) NOT NULL
);

CREATE TABLE account (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    owner_id BIGINT NOT NULL,
    number VARCHAR(20) NOT NULL UNIQUE CHECK (number SIMILAR TO '[0-9]+' AND length(number) >= 12),
    account_type VARCHAR(20) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    status VARCHAR(12) DEFAULT 0,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    closed_at TIMESTAMPTZ,
    version BIGINT NOT NULL DEFAULT 1,

    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES owner (id)
);

CREATE UNIQUE INDEX account_number_idx ON account (number);
CREATE INDEX account_idx ON account (id, owner_id);