package com.fsm.livraria.domain;

import io.micronaut.data.annotation.Embeddable;
import io.micronaut.data.annotation.Relation;

@Embeddable
public class CompraCupomId {

    @Relation(Relation.Kind.MANY_TO_ONE)
    private Compra compra;

    @Relation(Relation.Kind.MANY_TO_ONE)
    private Cupom cupom;

    public CompraCupomId() {
    }

    public CompraCupomId(Compra compra, Cupom cupom) {
        this.compra = compra;
        this.cupom = cupom;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Cupom getCupom() {
        return cupom;
    }

    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }
}
