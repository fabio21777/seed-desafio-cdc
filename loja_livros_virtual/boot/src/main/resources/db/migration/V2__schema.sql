CREATE TABLE autor (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    uuid UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    nome varchar(255) NOT NULL,
    email varchar(255) NOT NULL,
    descricao varchar(400));

    --adicionar constrains e index para uuid,

CREATE UNIQUE INDEX idx_autor_uuid ON autor (uuid);
CREATE INDEX idx_autor_nome ON autor (nome);