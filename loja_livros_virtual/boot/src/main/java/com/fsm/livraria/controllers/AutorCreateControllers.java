package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Autor;
import com.fsm.livraria.dto.AutorCreateRequest;
import com.fsm.livraria.dto.AutorDto;
import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import jakarta.validation.Valid;

@Controller
@Secured(SecurityRule.IS_ANONYMOUS)
public class AutorCreateControllers {

    private final AutorRepository autorRepository;

    public AutorCreateControllers(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    @Post("api/v1/author")
    public HttpResponse<AutorDto> create(@Body @Valid AutorCreateRequest request){
        Autor autor = autorRepository.save(request.name(),
                request.email(),
                request.description());
        return HttpResponse.created(new AutorDto(autor));
    }
}
