DROP TABLE free_account_numbers;
DROP TABLE account_number_sequence;

CREATE TABLE account_number_sequence
(
    account_type  VARCHAR(32)                             NOT NULL,
    current_count BIGINT                                  NOT NULL
);

CREATE TABLE free_account_numbers
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    account_type   VARCHAR(32)                             NOT NULL,
    account_number VARCHAR(32)                             NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE                NOT NULL,
    CONSTRAINT pk_free_account_numbers PRIMARY KEY (id)
);