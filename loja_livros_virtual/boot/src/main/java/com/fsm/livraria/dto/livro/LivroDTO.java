package com.fsm.livraria.dto.livro;

import com.fsm.livraria.domain.Livro;
import com.fsm.livraria.dto.Categoria.CategoriaDto;
import com.fsm.livraria.dto.autor.AutorDto;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class LivroDTO {

    private UUID uuid;

    private LocalDateTime createdAt;

    private String title;

    private String summary;

    private String content;

    private BigDecimal price;

    private Integer numberOfPages;

    private String isbn;

    private LocalDateTime publicationDate;

    private CategoriaDto category;

    private AutorDto author;

    public LivroDTO() {
    }

    public LivroDTO(Livro livro) {
        if (livro == null) {
            return;
        }
        this.uuid = livro.getUuid();
        this.createdAt = livro.getCreatedAt();
        this.title = livro.getTitulo();
        this.summary = livro.getResumo();
        this.content = livro.getSumario();
        this.price = livro.getPreco();
        this.numberOfPages = livro.getNumeroPaginas();
        this.isbn = livro.getIsbn();
        this.publicationDate = livro.getPublicacao();
        this.category = new CategoriaDto(livro.getCategoria());
        this.author = new AutorDto(livro.getAutor());
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(Integer numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    public CategoriaDto getCategory() {
        return category;
    }

    public void setCategory(CategoriaDto category) {
        this.category = category;
    }

    public AutorDto getAuthor() {
        return author;
    }

    public void setAuthor(AutorDto author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "LivroDTO{" +
                "uuid=" + uuid +
                ", createdAt=" + createdAt +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", numberOfPages=" + numberOfPages +
                ", isbn='" + isbn + '\'' +
                ", publicationDate=" + publicationDate +
                ", category=" + category +
                ", author=" + author +
                '}';
    }
}
