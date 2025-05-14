CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    uuid UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    titulo varchar(255) NOT NULL,
    resumo varchar(500) NOT NULL,
    sumario TEXT,
    preco decimal(10,2) NOT NULL,
    numero_paginas integer NOT NULL,
    isbn varchar(255) NOT NULL,
    publicacao timestamp NOT NULL,
    categoria_id BIGINT NOT NULL,
    autor_id BIGINT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id),
    FOREIGN KEY (autor_id) REFERENCES autor(id)
);

-- adicionar index unicos para uuid e isbn

CREATE UNIQUE INDEX idx_livro_uuid ON livro (uuid);
CREATE UNIQUE INDEX idx_livro_isbn ON livro (isbn);

-- adicionar index de  busca para titulo, resumo e sumario

CREATE INDEX idx_livro_titulo ON livro (titulo);
CREATE INDEX idx_livro_resumo ON livro (resumo);
CREATE INDEX idx_livro_sumario ON livro (sumario);

