-- Tabela para Compra
CREATE TABLE compra (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    sobrenome VARCHAR(255) NOT NULL,
    documento VARCHAR(20) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    complemento VARCHAR(255) NOT NULL,
    cidade VARCHAR(255) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    cep VARCHAR(20) NOT NULL,

    pais_id BIGINT NOT NULL,
    estado_id BIGINT,

    CONSTRAINT fk_compra_pais FOREIGN KEY (pais_id) REFERENCES pais (id),
    CONSTRAINT fk_compra_estado FOREIGN KEY (estado_id) REFERENCES estado (id)
);

-- Índices adicionais para performance
CREATE INDEX idx_compra_email ON compra(email);
CREATE INDEX idx_estado_pais_id ON estado(pais_id);
CREATE INDEX idx_compra_pais_id ON compra(pais_id);
CREATE INDEX idx_compra_estado_id ON compra(estado_id);