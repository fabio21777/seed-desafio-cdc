
CREATE TABLE cupom (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    codigo VARCHAR(64) NOT NULL,
    percentual_desconto NUMERIC(5, 2) NOT NULL,
    data_validade TIMESTAMP NOT NULL,
    CONSTRAINT uq_codigo UNIQUE (codigo)
);
--ADicionar index para o uuid e codigo

CREATE INDEX idx_cupom_uuid ON cupom(uuid);
CREATE INDEX idx_cupom_codigo ON cupom(codigo);