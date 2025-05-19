package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Compra;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface CompraRepository  extends CrudRepository <Compra, Long> {

    /*
    * essa consulta ela carrega todos os dados relacionados a compra para exibir na tela de detalhes, não use esse metodo para busca somente a compra
     */
    @Join("pais")
    @Join(value = "estado", type = Join.Type.LEFT)
    @Join(value = "carrinho")
    @Join(value = "carrinho.items")
    @Join(value = "carrinho.items.livro")
    @Join(value = "carrinho.items.livro.autor")
    @Join(value = "carrinho.items.livro.categoria")
    Optional<Compra> findByUuid(UUID uuid);

}
