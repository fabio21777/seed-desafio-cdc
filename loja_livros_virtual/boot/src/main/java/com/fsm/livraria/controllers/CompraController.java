package com.fsm.livraria.controllers;

import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraCupom;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.CompraCupomRepository;
import com.fsm.livraria.repositories.CompraRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.security.annotation.Secured;

import java.util.Optional;
import java.util.UUID;

@Controller
@Secured("ROLE_ADMIN")
public class CompraController {

    private static  final String URL = "api/v1/purchase";

    private final CompraRepository compraRepository;

    private final CompraCupomRepository compraCupomRepository;

    public CompraController(CompraRepository compraRepository, CompraCupomRepository compraCupomRepository) {
        this.compraRepository = compraRepository;
        this.compraCupomRepository = compraCupomRepository;
    }


    @Get(URL + "/{uuid}")
    public HttpResponse<CompraDto> getCompra(@PathVariable UUID uuid) {
        System.out.println("UUID: " + uuid);
        Optional<Compra> compra = compraRepository.findByUuid(uuid);
        if (compra.isEmpty()) {
            return HttpResponse.notFound();
        }
        Optional<CompraCupom> compraCupom = compraCupomRepository.findByIdCompra(compra.get());
        CompraDto compraDto = new CompraDto(compra.get(), compraCupom.orElse(null));

        return HttpResponse.ok(compraDto);

    }

}
