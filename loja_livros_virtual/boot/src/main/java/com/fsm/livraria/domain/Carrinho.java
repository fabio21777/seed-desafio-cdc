package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@MappedEntity
public class Carrinho extends BaseDomain {

    private BigDecimal total;

    @Size(min = 1, message = "A quantidade no carrinho deve ser no mínimo 1")
    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "carrinho", cascade = Relation.Cascade.PERSIST)
    private Set<CarrinhoItem> items = new HashSet<>();

    @Relation(value = Relation.Kind.ONE_TO_ONE)
    private Compra compra;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CarrinhoItem> getItems() {
        return items;
    }

    public void setItems(Set<CarrinhoItem> items) {
        this.items = items;
    }
}
