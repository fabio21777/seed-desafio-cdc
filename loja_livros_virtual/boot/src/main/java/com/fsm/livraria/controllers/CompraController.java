package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.dto.compra.CompraCreateRequest;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.*;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.transaction.annotation.Transactional;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class CompraController {

    private static  final String URL = "api/v1/purchase";

    public CompraController(CompraRepository compraRepository, EstadoRepository estadoRepository, PaisRepository paisRepository, LivroRepository livroRepository) {
        this.compraRepository = compraRepository;
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
        this.livroRepository = livroRepository;
    }

    private final CompraRepository compraRepository;

    private final EstadoRepository estadoRepository;

    private final PaisRepository paisRepository;

    private final LivroRepository livroRepository;



    @Post(URL)
    @Transactional
    public HttpResponse<CompraDto> create(@Valid @Body CompraCreateRequest request) {
        Compra compra = request.toEntity(estadoRepository, paisRepository, livroRepository);
        compra = compraRepository.save(compra);
        return HttpResponse.created(new CompraDto(compra));
    }
}
