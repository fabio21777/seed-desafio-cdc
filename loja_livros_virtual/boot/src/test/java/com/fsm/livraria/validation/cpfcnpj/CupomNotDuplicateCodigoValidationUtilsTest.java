package com.fsm.livraria.validation.cpfcnpj;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CupomNotDuplicateCodigoValidationUtilsTest {

    /**
     * Testa a validação de CPFs válidos
     */
    @Test
    void testCpfsValidos() {
        // CPFs válidos sem formatação
        assertTrue(CpfCnpjValidationUtils.isValid("32630145018"), "O CPF 32630145018 deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("91889334073"), "O CPF 91889334073 deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("64341058002"), "O CPF 64341058002 deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("69986628075"), "O CPF 69986628075 deveria ser considerado válido");
    }

    /**
     * Testa a validação de CNPJs válidos
     */
    @Test
    void testCnpjsValidos() {
        // CNPJs válidos sem formatação
        assertTrue(CpfCnpjValidationUtils.isValid("98511968000187"), "O CNPJ 98511968000187 deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("16481676000184"), "O CNPJ 16481676000184 deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("81244099000179"), "O CNPJ 81244099000179 deveria ser considerado válido");
    }

    /**
     * Testa a validação com CPFs formatados (com pontos e traço)
     */
    @Test
    void testCpfComFormatacao() {
        assertTrue(CpfCnpjValidationUtils.isValid("326.301.450-18"), "CPF formatado deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("918.893.340-73"), "CPF formatado deveria ser considerado válido");
    }

    /**
     * Testa a validação com CNPJs formatados (com pontos, barra e traço)
     */
    @Test
    void testCnpjComFormatacao() {
        assertTrue(CpfCnpjValidationUtils.isValid("98.511.968/0001-87"), "CNPJ formatado deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid("16.481.676/0001-84"), "CNPJ formatado deveria ser considerado válido");
    }

    /**
     * Testa a validação com CPFs inválidos
     */
    @Test
    void testCpfsInvalidos() {
        assertFalse(CpfCnpjValidationUtils.isValid("00000000000"), "CPF com todos dígitos iguais deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("12345678900"), "CPF com dígitos verificadores incorretos deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("111111111111"), "CPF com tamanho incorreto deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("123456789"), "CPF com tamanho incorreto deveria ser considerado inválido");
    }

    /**
     * Testa a validação com CNPJs inválidos
     */
    @Test
    void testCnpjsInvalidos() {
        assertFalse(CpfCnpjValidationUtils.isValid("00000000000000"), "CNPJ com todos dígitos iguais deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("12345678901234"), "CNPJ com dígitos verificadores incorretos deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("11111111111"), "CNPJ com tamanho incorreto deveria ser considerado inválido");
        assertFalse(CpfCnpjValidationUtils.isValid("1234567890123"), "CNPJ com tamanho incorreto deveria ser considerado inválido");
    }

    /**
     * Testa a validação com valores nulos e vazios
     */
    @Test
    void testValoresNulosEVazios() {
        assertTrue(CpfCnpjValidationUtils.isValid(null), "Valor nulo deveria ser considerado válido");
        assertTrue(CpfCnpjValidationUtils.isValid(""), "Valor vazio deveria ser considerado válido");
    }
}