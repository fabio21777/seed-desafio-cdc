package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.domain.Pais;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;


@JdbcRepository(dialect = Dialect.POSTGRES)
public interface EstadoRepository extends CrudRepository <Estado, Long> {

    Estado save(String nome, String sigla, Pais pais);

    boolean existsByNome(String name);

    boolean existsBySigla(String sigla);

}
