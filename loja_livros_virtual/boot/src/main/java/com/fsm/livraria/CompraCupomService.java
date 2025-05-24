package com.fsm.livraria;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.exceptions.exception.ServiceError;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraCupom;
import com.fsm.livraria.domain.Cupom;
import com.fsm.livraria.repositories.CompraCupomRepository;
import com.fsm.livraria.repositories.CompraRepository;
import com.fsm.livraria.repositories.CupomRepository;
import io.micronaut.core.util.StringUtils;
import jakarta.inject.Singleton;

@Singleton
public class CompraCupomService {


    private final CompraCupomRepository compraCupomRepository;

    private final CupomRepository cupomRepository;

    private final CompraRepository compraRepository;


    public CompraCupomService(CompraCupomRepository compraCupomRepository, CupomRepository cupomRepository, CompraRepository compraRepository) {
        this.compraCupomRepository = compraCupomRepository;
        this.cupomRepository = cupomRepository;
        this.compraRepository = compraRepository;
    }

    public CompraCupom vincularCupom(String cupom, Compra compra) {
        if (StringUtils.hasText(cupom)){
            Cupom cupomExistente = cupomRepository.findByCodigo(cupom)
                    .orElseThrow(() -> new NotFoundError("Cupom não encontrado"));

            if (cupomExistente.estaValido()) {
                CompraCupom cupomNew = new CompraCupom(compra, cupomExistente);
                compra.calcularValorFinal(cupomNew);
                compraCupomRepository.save(cupomNew);
                compraRepository.update(compra);
                return  cupomNew;
            }else {
                throw new ServiceError("Cupom inválido, a data de validade já passou");
            }
        }
        return null;
    }

}
