
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


CREATE TABLE compra_cupom (
    id_compra_id BIGINT NOT NULL,
    id_cupom_id BIGINT NOT NULL,
    valor_desconto NUMERIC(10, 2) NOT NULL,
    FOREIGN KEY (id_compra_id) REFERENCES compra(id),
    FOREIGN KEY (id_cupom_id) REFERENCES cupom(id),
    PRIMARY KEY (id_compra_id, id_cupom_id)
);
--adicionar o valor_final a compra
ALTER TABLE compra
ADD COLUMN valor_final NUMERIC(10, 2) NULL DEFAULT 0;