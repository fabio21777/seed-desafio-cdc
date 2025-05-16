package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Pais;
import com.fsm.livraria.dto.paisestado.PaisCreateRequest;
import com.fsm.livraria.dto.paisestado.PaisDto;
import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class PaisController {

    private final PaisRepository paisRepository;

    private final static String PATH = "/api/v1/country";

    public PaisController(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }


    @Post(PATH)
    public HttpResponse<PaisDto> create(@Valid @Body PaisCreateRequest request) {
        Pais pais = paisRepository.save(request.getName(), request.getAbbreviation());
        return HttpResponse.created(new PaisDto(pais));
    }


}
