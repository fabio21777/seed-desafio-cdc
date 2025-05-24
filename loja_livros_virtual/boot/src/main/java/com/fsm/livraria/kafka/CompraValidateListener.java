package com.fsm.livraria.kafka;

import com.fsm.exceptions.exception.NotFoundError;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraStatus;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.repositories.CompraRepository;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener
public class CompraValidateListener {

    private final CompraRepository repository;

    private static final Logger logger = LoggerFactory.getLogger(CompraValidateListener.class);

    public CompraValidateListener(CompraRepository repository) {
        this.repository = repository;
    }

    @Topic("validar-compra")
    public void validarComprar(CompraDto compra){

        try { //caso o corro algum erro no processamento e o erro não for tratado a configuração padrão não vai deixar seguir para a proxima mensage é boa prática sempre tratar os erros das mensagem
            logger.info("COMPRA LIDA");

            logger.info(compra.toString());

            //logica de validação
            Compra compraEntity = repository.findByUuidLazy(compra.getUuid()).orElseThrow(() -> new NotFoundError("compra não encontrada"));

            compraEntity.setStatus(CompraStatus.FINALIZADA);

            repository.update(compraEntity);

            logger.info("======================processamento kafka finalizado com sucesso :)=================================================");

        }catch (Exception e){
            logger.error("erro - {}", e.getMessage(), e);

            //aqui o ideal e envia para uma fila morta o conceito de dead letter queue, para processar depois

        }

    }

}
