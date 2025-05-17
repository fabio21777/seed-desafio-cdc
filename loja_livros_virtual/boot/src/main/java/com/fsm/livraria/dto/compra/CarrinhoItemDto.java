package com.fsm.livraria.dto.compra;

import com.fsm.livraria.domain.CarrinhoItem;
import com.fsm.livraria.dto.livro.LivroDTO;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Serdeable
public class CarrinhoItemDto {
    private LivroDTO livro;

    private int quatity;

    public CarrinhoItemDto() {
    }

    public CarrinhoItemDto(CarrinhoItem carrinhoItem) {
        if (carrinhoItem == null) {
            return;
        }
        this.livro = new LivroDTO(carrinhoItem.getLivro());
        this.quatity = (int) carrinhoItem.getQuantidade();
    }

    public LivroDTO getLivro() {
        return livro;
    }

    public void setLivro(LivroDTO livro) {
        this.livro = livro;
    }

    public int getQuatity() {
        return quatity;
    }

    public void setQuatity(int quatity) {
        this.quatity = quatity;
    }
}
