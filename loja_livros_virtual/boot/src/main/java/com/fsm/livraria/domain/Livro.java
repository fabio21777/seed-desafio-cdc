package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.min;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedEntity
public class Livro extends BaseDomain {

    @NotBlank
    private String titulo;

    @NotBlank
    private String resumo;


    private String sumario; //md ou html

    @NotNull
    @Min(value = 20)
    private BigDecimal preco;

    @NotNull
    private Integer numeroPaginas;

    @NotBlank
    private String isbn;

    @NotNull
    private LocalDateTime publicacao;

    @NotNull
    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Categoria categoria;

    @NotNull
    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Autor autor;

    public Livro(@NotBlank String titulo, @NotBlank String resumo, String sumario, @Min(value = 20) BigDecimal preco, @Min(value = 100) Integer numeroPaginas, @NotBlank String isbn, @NotBlank LocalDateTime publicacao, @NotNull Categoria categoria, @NotNull Autor autor) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.sumario = sumario;
        this.preco = preco;
        this.numeroPaginas = numeroPaginas;
        this.isbn = isbn;
        this.publicacao = publicacao;
        this.categoria = categoria;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getSumario() {
        return sumario;
    }

    public void setSumario(String sumario) {
        this.sumario = sumario;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Integer getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(Integer numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getPublicacao() {
        return publicacao;
    }

    public void setPublicacao(LocalDateTime publicacao) {
        this.publicacao = publicacao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
