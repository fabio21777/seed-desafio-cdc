package com.fsm.livraria.dto.compra;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.exceptions.exception.ServiceError;
import com.fsm.livraria.domain.Carrinho;
import com.fsm.livraria.domain.CarrinhoItem;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.Livro;
import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Serdeable
public class CarrinhoRequest {

    @NotNull
    @Min(value =  1 , message = "Quantidade deve ser maior que 0")
    private BigDecimal total;

    @Size(min = 1, message = "A quantidade no carrinho deve ser no mínimo 1")
    @NotNull(message = "O carrinho não pode ser vazio")
    @Valid
    private Set<CarrinhoItenRequest> items;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Set<CarrinhoItenRequest> getItems() {
        return items;
    }

    public void setItems(Set<CarrinhoItenRequest> items) {
        this.items = items;
    }

    Set<UUID> getLivrosUuids() {
        return this.items.stream()
                .map(CarrinhoItenRequest::getLivro)
                .collect(Collectors.toSet());
    }


    public Carrinho toEntity(LivroRepository livroRepository, Compra compra) {
        // Buscar todos os livros do carrinho de uma vez
        Set<UUID> livroIds = this.getLivrosUuids();

        Map<UUID, Livro> livroMap = livroRepository.findAllByUuidIn(livroIds).stream()
                .collect(Collectors.toMap(Livro::getUuid, livro -> livro));

        //validar se o valor total é igual ao valor dos livros
        BigDecimal totalCalculado = livroMap.values().stream()
                .map(Livro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalCalculado.compareTo(this.total) != 0) {
            throw new ServiceError("O valor total do carrinho não é igual ao valor dos livros");
        }

        // Criar o carrinho primeiro
        Carrinho carrinho = new Carrinho();
        carrinho.setTotal(this.total);
        carrinho.setCompra(compra);

        // Adicionar cada item individualmente ao carrinho
        List<CarrinhoItem> carrinhoItems = new ArrayList<>();
        this.items.forEach(itemRequest -> {
            UUID livroId = itemRequest.getLivro();
            Livro livro = livroMap.get(livroId);

            if (livro == null) {
                throw new NotFoundError("Livro não encontrado com UUID: " + livroId);
            }
            CarrinhoItem item = itemRequest.toEntity(livro, carrinho);
            carrinhoItems.add(item);
        });
        carrinho.getItems().addAll(carrinhoItems);
        return carrinho;
    }
}
