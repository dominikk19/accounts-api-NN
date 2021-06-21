CREATE TABLE IF NOT EXISTS ACCOUNTS(
    uuid                UUID NOT NULL PRIMARY KEY,
    balance_in_pln      NUMERIC(19,2) not null,
    balance_in_usd      NUMERIC(19,2) not null,
    firstname           VARCHAR(100) not null,
    surname             VARCHAR(100) not null
);
