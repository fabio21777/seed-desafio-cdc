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

CREATE TABLE carrinho (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    total NUMERIC(10, 2) NOT NULL,
    compra_id BIGINT NOT NULL,

    CONSTRAINT fk_carrinho_compra FOREIGN KEY (compra_id) REFERENCES compra (id)
);

-- Tabela para item do carrinho
CREATE TABLE carrinho_item (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    quantidade BIGINT NOT NULL,

    livro_id BIGINT NOT NULL,
    carrinho_id BIGINT NOT NULL,

    CONSTRAINT fk_carrinho_item_livro FOREIGN KEY (livro_id) REFERENCES livro (id),
    CONSTRAINT fk_carrinho_item_carrinho FOREIGN KEY (carrinho_id) REFERENCES carrinho (id)
);


-- Índices adicionais para performance
CREATE INDEX idx_compra_email ON compra(email);
CREATE INDEX idx_estado_pais_id ON estado(pais_id);
CREATE INDEX idx_compra_pais_id ON compra(pais_id);
CREATE INDEX idx_compra_estado_id ON compra(estado_id);
CREATE INDEX idx_carrinho_item_livro_id ON carrinho_item(livro_id);
CREATE INDEX idx_carrinho_item_carrinho_id ON carrinho_item(carrinho_id);
CREATE INDEX idx_carrinho_item_uuid ON carrinho_item(uuid);
CREATE INDEX idx_carrinho_uuid ON carrinho(uuid);
CREATE INDEX idx_compra_uuid ON compra(uuid);