package com.fsm.livraria.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Autor;
import com.fsm.livraria.domain.Categoria;
import com.fsm.livraria.domain.Livro;
import com.fsm.livraria.repositories.AutorRepository;
import com.fsm.livraria.repositories.CategoriaRepository;
import com.fsm.livraria.validation.Livro.LivroNotDuplicateIsbn;
import com.fsm.livraria.validation.Livro.LivroNotDuplicateTitle;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
@LivroNotDuplicateTitle(message = "já existe um livro com este título")
@LivroNotDuplicateIsbn (message = "já existe um livro com este ISBN")
public class LivroCreateRequestDto {

    private UUID uuid;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 255, message = "O título deve ter menos de 255 caracteres")
    private String title;

    @NotBlank(message = "O resumo é obrigatório")
    @Size(max = 500, message = "O resumo deve ter no máximo 500 caracteres")
    private String summary;

    private String content;

    @NotNull(message = "O preço é obrigatório")
    @Min(value = 20, message = "O preço deve ser de no mínimo 20")
    private BigDecimal price;

    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 100, message = "O livro deve ter no mínimo 100 páginas")
    private Integer numberOfPages;

    @NotBlank(message = "O ISBN é obrigatório")
    private String isbn;

    @NotNull(message = "A data de publicação é obrigatória")
    @Future(message = "A data de publicação deve ser no futuro")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime publicationDate;

    @NotNull(message = "A categoria é obrigatória")
    private UUID category;

    @NotNull(message = "O autor é obrigatório")
    private UUID author;

    public LivroCreateRequestDto() {
    }

    public LivroCreateRequestDto(String title, String summary, String content, BigDecimal price, Integer numberOfPages, String isbn, LocalDateTime publicationDate, UUID category, UUID author) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.price = price;
        this.numberOfPages = numberOfPages;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.category = category;
        this.author = author;
    }


    public @NonNull Livro toEntity(AutorRepository autorRepository, CategoriaRepository categoriaRepository) {

        Autor autor = autorRepository.findByUuid(author)
                .orElseThrow(() -> new NotFoundError("Autor não encontrado"));

        Categoria categoria = categoriaRepository.findByUuid(category)
                .orElseThrow(() -> new NotFoundError("Categoria não encontrada"));

        return new Livro(this.title,
                this.summary,
                this.content,
                this.price,
                this.numberOfPages,
                this.isbn,
                this.publicationDate,
                categoria,
                autor);
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

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public UUID getAuthor() {
        return author;
    }

    public void setAuthor(UUID author) {
        this.author = author;
    }
}
