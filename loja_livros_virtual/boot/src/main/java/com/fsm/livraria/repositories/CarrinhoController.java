package com.fsm.livraria.repositories;

import com.fsm.livraria.domain.Carrinho;
import io.micronaut.data.repository.CrudRepository;

public interface CarrinhoController extends CrudRepository <Carrinho, Long> {
}
