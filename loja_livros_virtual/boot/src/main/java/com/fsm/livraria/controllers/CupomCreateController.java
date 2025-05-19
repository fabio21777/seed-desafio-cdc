package com.fsm.livraria.controllers;

import com.fsm.livraria.dto.cupom.CupomCreateRequest;
import com.fsm.livraria.dto.cupom.CupomDto;
import com.fsm.livraria.repositories.CupomRepository;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import jakarta.validation.Valid;

@Controller
@Secured("ROLE_ADMIN")
public class CupomCreateController {

    private static final String URL = "api/v1/coupon";

    private final CupomRepository cupomRepository;

    public CupomCreateController(CupomRepository cupomRepository) {
        this.cupomRepository = cupomRepository;
    }


    @Post(URL)
    public HttpResponse<CupomDto> create(@Valid @Body CupomCreateRequest request) {
        return HttpResponse.created(new CupomDto(cupomRepository.save(request.getCode(),
                request.getPercentageDiscount(), request.getDateValidity())));
    }
}
