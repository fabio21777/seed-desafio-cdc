package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.Pais;
import com.fsm.livraria.dto.compra.CompraCreateRequest;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.CompraRepository;
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
public class CompraController {

    private static  final String URL = "api/v1/purchase";

    private final CompraRepository compraRepository;

    private final EstadoRepository estadoRepository;

    private final PaisRepository paisRepository;

    public CompraController(CompraRepository compraRepository, EstadoRepository estadoRepository, PaisRepository paisRepository) {
        this.compraRepository = compraRepository;
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
    }

    @Post(URL)
    public HttpResponse<CompraDto> create(@Valid @Body CompraCreateRequest request) {
        Compra compra = request.toEntity(estadoRepository, paisRepository);
        compra = compraRepository.save(compra);
        return HttpResponse.created(new CompraDto(compra));
    }
}
