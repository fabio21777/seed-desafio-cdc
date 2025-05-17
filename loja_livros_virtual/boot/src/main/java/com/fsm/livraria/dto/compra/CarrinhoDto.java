package com.fsm.livraria.dto.compra;

import com.fsm.livraria.domain.Carrinho;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Serdeable
public class CarrinhoDto {

    private BigDecimal total;

    private Set<CarrinhoItemDto> items;

    public CarrinhoDto() {
    }

    public  CarrinhoDto(Carrinho carrinho) {
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
