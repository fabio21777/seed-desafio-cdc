package com.fsm.livraria.domain;

import io.micronaut.data.annotation.EmbeddedId;
import io.micronaut.data.annotation.MappedEntity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@MappedEntity
public class CompraCupom {

    @EmbeddedId
    private CompraCupomId id;

    private BigDecimal valorDesconto;

    // Construtor padrão exigido pelo Micronaut Data
    public CompraCupom() {
    }

    // Construtor que recebe a Compra e o Cupom
    public CompraCupom(Compra compra, Cupom cupom) {
        this.id = new CompraCupomId(compra, cupom);
        // o valor do desconto vem em % ou seja de 1 a 100
        this.valorDesconto = compra.getCarrinho().getTotal()
                .multiply(cupom.getPercentualDesconto())
                .divide(new BigDecimal(100), RoundingMode.HALF_UP);
    }

    public CompraCupomId getId() {
        return id;
    }

    public void setId(CompraCupomId id) {
        this.id = id;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
