CREATE TABLE IF NOT EXISTS ACCOUNTS(
    uuid                UUID NOT NULL PRIMARY KEY,
    balance             NUMERIC(19,2) not null,
    currency            VARCHAR(3) not null,
    firstname           VARCHAR(100) not null,
    surname             VARCHAR(100) not null
);
