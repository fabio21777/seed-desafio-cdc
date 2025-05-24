package com.fsm.livraria.kafka;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraStatus;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.CompraRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaListener
public class CompraValidateListener {

    private final CompraRepository repository;

    public CompraValidateListener(CompraRepository repository) {
        this.repository = repository;
    }

    @Topic("validar-compra")
    public void validarComprar(CompraDto compra){

        System.out.println("COMPRA LIDA");

        System.out.println(compra.toString());

        /**
         * logica de validação a partir daqui
         * */

        Compra compraEntity = repository.findByUuidLazy(compra.getUuid()).orElseThrow(() -> new NotFoundError("compra não encontrada"));

        compraEntity.setStatus(CompraStatus.FINALIZADA);

        repository.update(compraEntity);

        System.out.println("======================processamento kafka finalizado com sucesso :)=================================================");

    }

}
