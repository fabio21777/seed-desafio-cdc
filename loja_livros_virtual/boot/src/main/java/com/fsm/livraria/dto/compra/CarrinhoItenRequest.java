package com.fsm.livraria.dto.compra;

import com.fsm.livraria.domain.Carrinho;
import com.fsm.livraria.domain.CarrinhoItem;
import com.fsm.livraria.domain.Livro;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Serdeable
public class CarrinhoItenRequest {

    @NotNull(message = "O livro deve ser informado")
    private UUID livro;

    @NotNull(message = "A quantidade deve ser informada")
    @Min(value = 1, message = "A quantidade deve ser maior que 0")
    private int quatity;

    public UUID getLivro() {
        return livro;
    }

    public void setLivro(UUID livro) {
        this.livro = livro;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }

    public CarrinhoItem toEntity(Livro livro, Carrinho carrinho) {
        CarrinhoItem item = new CarrinhoItem();
        item.setQuantidade(this.quatity);
        item.setCarrinho(carrinho);
        item.setLivro(livro);
        return item;
    }
}
