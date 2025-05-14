package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Livro;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface LivroRepository extends CrudRepository<Livro, Long> {

    boolean existsByTitulo(String titulo);

    boolean existsByTituloAndUuidNot(String titulo, UUID uuid);

    boolean existsByIsbn(String isbn);

    boolean existsByIsbnAndUuidNot(String isbn, UUID uuid);

}
