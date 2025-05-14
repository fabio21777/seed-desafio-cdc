package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Categoria;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect =  Dialect.POSTGRES)
public interface CategoriaRepository extends CrudRepository<Categoria, Long> {
    Categoria save(String nome);
    boolean existsByNomeAndUuidNot(String nome, UUID uuid);
    boolean existsByNome(String nome);
    Optional<Categoria> findByNome(String nome);
}
