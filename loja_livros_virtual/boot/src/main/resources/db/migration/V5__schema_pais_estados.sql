CREATE  TABLE pais (
    id SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(255) NOT NULL,
    sigla VARCHAR(2) NOT NULL
);
-- add unique constraint to sigla e nome e uuid
CREATE UNIQUE INDEX idx_pais_nome ON pais (nome);
CREATE UNIQUE INDEX idx_pais_uuid ON pais (uuid);

CREATE TABLE estado
(
    id SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    nome VARCHAR(255) NOT NULL,
    sigla VARCHAR(2) NOT NULL,
    pais_id BIGINT NOT NULL,
    FOREIGN KEY (pais_id) REFERENCES pais(id) ON DELETE CASCADE
);

-- add unique constraint to sigla e nome e uuid
CREATE UNIQUE INDEX idx_estado_nome ON estado (nome);
CREATE UNIQUE INDEX idx_estado_uuid ON estado (uuid);



