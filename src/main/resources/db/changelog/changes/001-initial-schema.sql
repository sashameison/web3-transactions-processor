CREATE SEQUENCE transaction_id_seq
    START WITH 1
    INCREMENT BY 20
    NO MINVALUE
    NO MAXVALUE
    CACHE 20;

CREATE TABLE IF NOT EXISTS transactions
(
    transaction_id BIGINT PRIMARY KEY DEFAULT NEXTVAL('transaction_id_seq'),
    hash VARCHAR(128),
    block_hash VARCHAR(128),
    transaction_index NUMERIC(100, 2),
    block_number NUMERIC(100),
    transfer_from VARCHAR(128),
    transfer_to VARCHAR(128),
    transfer_value NUMERIC(100, 2),
    created_at TIMESTAMP
);
