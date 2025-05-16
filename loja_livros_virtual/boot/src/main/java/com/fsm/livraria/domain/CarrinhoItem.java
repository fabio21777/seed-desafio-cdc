package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

@MappedEntity
public class CarrinhoItem extends BaseDomain {
    private long quantidade;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Livro livro;

    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Carrinho carrinho;

    public CarrinhoItem() {
    }

    public CarrinhoItem(long quantidade, Livro livro) {
        this.quantidade = quantidade;
        this.livro = livro;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}
