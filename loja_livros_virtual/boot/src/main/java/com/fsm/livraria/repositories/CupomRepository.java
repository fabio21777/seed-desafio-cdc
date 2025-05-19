package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Cupom;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface CupomRepository  extends CrudRepository <Cupom, Long> {

    Cupom save(String codigo, BigDecimal percentualDesconto, LocalDateTime dataValidade);

    Optional<Cupom> findByCodigo(String codigo);

    boolean existsByCodigo(String code);
}
