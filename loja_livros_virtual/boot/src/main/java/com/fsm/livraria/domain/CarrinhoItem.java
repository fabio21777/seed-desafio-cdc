package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import java.util.Objects;

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

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CarrinhoItem that = (CarrinhoItem) o;
        return quantidade == that.quantidade && Objects.equals(livro, that.livro) && Objects.equals(carrinho, that.carrinho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), quantidade, livro, carrinho);
    }
}
