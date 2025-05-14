package com.fsm.livraria.repositories;


import com.fsm.livraria.domain.Autor;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface AutorRepository extends CrudRepository<Autor, Long> {

    Autor save(@NotNull String nome, @NotNull String email, @NotNull String descricao);

    boolean existsByEmailAndUuidNot(String email, UUID uuid);
    boolean existsByEmail(String email);

    Optional<Autor> findByEmail(String email);

    Optional<Autor> findByUuid(UUID uuid);
}
