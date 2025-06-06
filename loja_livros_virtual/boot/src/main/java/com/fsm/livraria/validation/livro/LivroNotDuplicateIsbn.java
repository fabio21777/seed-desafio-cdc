package com.fsm.livraria.validation.livro;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * A anotação indica que o ISBN não deve existir duplicado no banco de dados.
 * Durante uma operação de atualização, ignorará a entidade atual sendo atualizada.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LivroNotDuplicateIsbn.List.class)
@Documented
@Constraint(validatedBy = {})
public @interface LivroNotDuplicateIsbn {

    String MESSAGE = "micronaut.NotDuplicateIsbn.message";

    /**
     * @return message A mensagem de erro
     */
    String message() default "{" + MESSAGE + "}";

    /**
     * @return Groups para controlar a ordem de validação das constraints
     */
    Class<?>[] groups() default {};

    /**
     * @return Payloads usados pelos clientes de validação
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Anotação de lista.
     */
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER, ElementType.TYPE_USE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        /**
         * @return Um array de NotDuplicateIsbn.
         */
        LivroNotDuplicateIsbn[] value();
    }
}