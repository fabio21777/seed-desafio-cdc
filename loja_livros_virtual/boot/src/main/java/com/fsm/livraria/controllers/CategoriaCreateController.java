package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Categoria;
import com.fsm.livraria.dto.CategoriaCreateRequest;
import com.fsm.livraria.dto.CategoriaDto;
import com.fsm.livraria.repositories.CategoriaRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class CategoriaCreateController {

    private  final CategoriaRepository categoriaRepository;


    public CategoriaCreateController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("api/v1/categories")
    public HttpResponse<CategoriaDto> create(@Body @Valid CategoriaCreateRequest request) {
        Categoria categoria = categoriaRepository.save(request.getName());
        return HttpResponse.created(new CategoriaDto(categoria));
    }
}
