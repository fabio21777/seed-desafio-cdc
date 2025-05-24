package com.fsm.livraria.kafka;

import com.fsm.livraria.dto.compra.CompraDto;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.scheduling.annotation.Async;

@KafkaClient
public interface CompraValidateProduce {
    @Topic("validar-compra")
    @Async
    void validarComprar(CompraDto compra);
}
