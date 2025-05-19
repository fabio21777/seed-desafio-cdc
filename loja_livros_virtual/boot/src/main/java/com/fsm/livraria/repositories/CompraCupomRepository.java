package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraCupom;
import com.fsm.livraria.domain.CompraCupomId;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface CompraCupomRepository extends CrudRepository<CompraCupom, CompraCupomId> {

    @Join("id.cupom")
    Optional<CompraCupom> findByIdCompra(Compra compra);
}