package com.fsm.livraria.dto.compra;

import com.fsm.livraria.domain.Carrinho;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Serdeable
public class CarrinhoDto {

    private BigDecimal total;

    private Set<CarrinhoItemDto> items;

    public CarrinhoDto() {
    }

    public  CarrinhoDto(Carrinho carrinho) {
        if (carrinho == null) {
            return;
        }
        this.total = carrinho.getTotal();
        this.items = carrinho.getItems().stream()
                .map(CarrinhoItemDto::new)
                .collect(Collectors.toSet());
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CarrinhoItemDto> getItems() {
        return items;
    }

    public void setItems(Set<CarrinhoItemDto> items) {
        this.items = items;
    }
}
