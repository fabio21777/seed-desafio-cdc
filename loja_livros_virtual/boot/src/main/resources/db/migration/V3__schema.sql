CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    uuid UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    nome varchar(255) NOT NULL
);
-- adicionar constrains e index para uuid é unique e nome é unique
CREATE UNIQUE INDEX idx_categoria_uuid ON categoria (uuid);
CREATE UNIQUE INDEX idx_categoria_nome ON categoria (nome);