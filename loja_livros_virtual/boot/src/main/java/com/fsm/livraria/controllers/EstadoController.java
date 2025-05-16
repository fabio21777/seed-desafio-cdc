package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.dto.paisestado.EstadoCreateRequest;
import com.fsm.livraria.dto.paisestado.EstadoDto;
import com.fsm.livraria.repositories.EstadoRepository;
import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class EstadoController {

    private final EstadoRepository estadoRepository;

    private final PaisRepository paisRepository;

    private static  final String PATH = "api/v1/state";

    public EstadoController(EstadoRepository estadoRepository, PaisRepository paisRepository) {
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
    }

    @Post(PATH)
    public HttpResponse<EstadoDto> create(@Valid @Body EstadoCreateRequest request) {
        Estado estado = request.toEntity(paisRepository);
        estado = estadoRepository.save(estado);
        return HttpResponse.created(new EstadoDto(estado));
    }
}
