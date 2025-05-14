package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Livro;
import com.fsm.livraria.dto.LivroCreateRequestDto;
import com.fsm.livraria.dto.LivroDTO;
import com.fsm.livraria.dto.LivroList;
import com.fsm.livraria.repositories.AutorRepository;
import com.fsm.livraria.repositories.CategoriaRepository;
import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

import java.security.Principal;

@Controller
@Secured("ROLE_ADMIN")
public class LivroController {

    private final LivroRepository livroRepository;

    private final AutorRepository autorRepository;

    private final CategoriaRepository categoriaRepository;

    public LivroController(LivroRepository livroRepository, AutorRepository autorRepository, CategoriaRepository categoriaRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Post("api/v1/livros")
    public HttpResponse<LivroDTO> create(@Body @Valid LivroCreateRequestDto request, Principal principal) {
        Livro livro = livroRepository.save(request.toEntity(autorRepository,
                categoriaRepository));
        return HttpResponse.created(new LivroDTO(livro));
    }

    @Get("api/v1/livros")
    public HttpResponse<Page<LivroList>> list(@Valid Pageable page) {
        Page<LivroList> livros = livroRepository.findAllLivros(page);
        return HttpResponse.ok(livros);
    }

}

