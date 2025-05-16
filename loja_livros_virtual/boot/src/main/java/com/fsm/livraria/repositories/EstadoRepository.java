package com.fsm.livraria.repositories;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.domain.Pais;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;


@JdbcRepository(dialect = Dialect.POSTGRES)
public interface EstadoRepository extends CrudRepository <Estado, Long> {

    Estado save(String nome, String sigla, Pais pais);

    boolean existsByNome(String name);

    boolean existsBySigla(String sigla);

    boolean existsByPais(Pais pais);

    Optional<Estado> findByUuid(UUID uuid);

    default  Estado findByUuidOrElseThrow(UUID uuid) {
        return findByUuid(uuid).orElseThrow(() -> new NotFoundError("Estado não encontrado"));
    }
}
