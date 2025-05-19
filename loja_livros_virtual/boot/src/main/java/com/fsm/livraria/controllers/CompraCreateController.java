package com.fsm.livraria.controllers;

import com.fsm.livraria.CompraCupomService;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraCupom;
import com.fsm.livraria.dto.compra.CompraCreateRequest;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.CompraRepository;
import com.fsm.livraria.repositories.EstadoRepository;
import com.fsm.livraria.repositories.LivroRepository;
import com.fsm.livraria.repositories.PaisRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class CompraCreateController {

    private static  final String URL = "api/v1/purchase";

    private final CompraRepository compraRepository;

    private final EstadoRepository estadoRepository;

    private final PaisRepository paisRepository;

    private final LivroRepository livroRepository;

    private final CompraCupomService compraCupomService;


    public CompraCreateController(CompraRepository compraRepository, EstadoRepository estadoRepository, PaisRepository paisRepository, LivroRepository livroRepository, CompraCupomService compraCupomService) {
        this.compraRepository = compraRepository;
        this.estadoRepository = estadoRepository;
        this.paisRepository = paisRepository;
        this.livroRepository = livroRepository;
        this.compraCupomService = compraCupomService;
    }


    @Post(URL)
    public HttpResponse<CompraDto> create(@Valid @Body CompraCreateRequest request) {
        Compra compra = request.toEntity(estadoRepository, paisRepository, livroRepository);
        compra = compraRepository.save(compra);
        CompraCupom compraCupom = compraCupomService.vincularCupom(request.getCoupon(), compra);

        return HttpResponse.created(new CompraDto(compra, compraCupom));
    }
}
