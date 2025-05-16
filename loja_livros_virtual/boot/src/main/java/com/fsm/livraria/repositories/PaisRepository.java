package com.fsm.livraria.repositories;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Pais;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect =  Dialect.POSTGRES)
public interface PaisRepository extends CrudRepository <Pais, Long> {

    Pais save(String nome, String sigla);

    boolean existsByNome(String nome);

    boolean existsBySigla(String sigla);

    Optional<Pais> findByUuid(UUID uuid);

    default Pais findByUuidOrElseThrow(UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new NotFoundError("País não encontrado"));
    }
}
