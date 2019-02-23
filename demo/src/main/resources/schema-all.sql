DROP TABLE pessoa IF EXISTS;

CREATE TABLE pessoa  (
    codigo BIGINT IDENTITY NOT NULL PRIMARY KEY,
    nome VARCHAR(20),
    sobre_nome VARCHAR(20)
);
