package com.fsm.livraria.controllers.cupom;

import com.fsm.UtilsTest;
import com.fsm.livraria.dto.cupom.CupomCreateRequest;
import com.fsm.livraria.dto.cupom.CupomDto;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fsm.UtilsTest.CreateValorAleatorio0a100;
import static com.fsm.livraria.controllers.cupom.GeradorCodigoLegivel.criarCodigoLegivel;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class CupomCreateControllerTest {

    @Inject
    RequestSpecification spec;

    @Inject
    UtilsTest utilsTest;

    private static final String PATH = "api/v1/coupon";

    @Test
    void testeSanity() {
        assertTrue(true);
    }

    @Test
    @DisplayName("criar um novo cupom")
    void criarNovoCupom() {
        CupomCreateRequest request = criarRequest();
        String token = utilsTest.getToken();
        Response response = spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201)
                .extract().response();

        assertNotNull(response);
        CupomDto cupom = response.as(CupomDto.class);
        assertNotNull(cupom);

        assertNotNull(cupom.getUuid());
        assertEquals(request.getCode(), cupom.getCode());
        assertEquals(request.getPercentageDiscount(), cupom.getPercentageDiscount());
    }

    @Test
    @DisplayName("deve aceitar código com números no início")
    void testCodigoComNumerosNoInicio() {
        CupomCreateRequest request = criarRequest();
        request.setCode("123" + request.getCode());

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("deve aceitar código com números no final")
    void testCodigoComNumerosNoFinal() {
        CupomCreateRequest request = criarRequest();
        request.setCode(request.getCode() + "123");

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201);
    }


    @Test
    @DisplayName("deve retornar erro quando código está em branco")
    void testCodigoEmBranco() {
        CupomCreateRequest request = criarRequest();
        request.setCode(""); // Código em branco

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O código do cupom deve ser informado"));
    }

    @Test
    @DisplayName("deve retornar erro quando código tem menos de 3 caracteres")
    void testCodigoMuitoCurto() {
        CupomCreateRequest request = criarRequest();
        request.setCode("a1"); // Código muito curto

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O código do cupom deve ter entre 3 e 64 caracteres"));
    }

    @Test
    @DisplayName("deve retornar erro quando código tem mais de 64 caracteres")
    void testCodigoMuitoLongo() {
        CupomCreateRequest request = criarRequest();
        // Criar código com 65 caracteres
        request.setCode("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmn12");

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O código do cupom deve ter entre 3 e 64 caracteres"));
    }

    @Test
    @DisplayName("deve retornar erro quando código não contém vogais")
    void testCodigoSemVogais() {
        CupomCreateRequest request = criarRequest();
        // Código sem vogais
        request.setCode("bcdfg123");

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O código do cupom deve ser de fácil leitura"));
    }

    @Test
    @DisplayName("deve retornar erro quando código tem muitas consoantes consecutivas")
    void testCodigoComMuitasConsoantesConsecutivas() {
        CupomCreateRequest request = criarRequest();
        // Código com 4 consoantes consecutivas
        request.setCode("abstklm23");

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O código do cupom deve ser de fácil leitura"));
    }

    @Test
    @DisplayName("deve aceitar código com caracteres especiais do português")
    void testCodigoComCaracteresEspeciaisPortugues() {
        CupomCreateRequest request = criarRequest();
        request.setCode("promoçãoéçá" + request.getCode());

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("deve retornar erro quando código já existe")
    void testCodigoDuplicado() {
        // Primeiro, cria um cupom
        CupomCreateRequest request1 = criarRequest();
        String token = utilsTest.getToken();

        Response response = spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request1)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201)
                .extract().response();

        // Tenta criar outro cupom com o mesmo código
        CupomCreateRequest request2 = criarRequest();
        request2.setCode(request1.getCode());

        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request2)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Já existe um cupom com este código"));
    }

    @Test
    @DisplayName("deve retornar erro quando percentual de desconto é menor que 1")
    void testPercentualDescontoMenorQueUm() {
        CupomCreateRequest request = criarRequest();
        request.setPercentageDiscount(BigDecimal.ZERO);

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O valor do desconto deve ser maior que 0"));
    }

    @Test
    @DisplayName("deve retornar erro quando percentual de desconto é maior que 100")
    void testPercentualDescontoMaiorQueCem() {
        CupomCreateRequest request = criarRequest();
        request.setPercentageDiscount(BigDecimal.valueOf(101));

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("O valor do desconto deve ser menor que 100"));
    }

    @Test
    @DisplayName("deve retornar erro quando data de validade está no passado")
    void testDataValidadeNoPasado() {
        CupomCreateRequest request = criarRequest();
        request.setDateValidity(LocalDateTime.now().minusDays(1));

        String token = utilsTest.getToken();
        spec.when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("A data de validade deve ser uma data futura"));
    }

    //===============================================Métodos auxiliares=========================================================

    private CupomCreateRequest criarRequest() {
        return new CupomCreateRequest(
                criarCodigoLegivel(),
                CreateValorAleatorio0a100(),
                LocalDateTime.now().plusDays(1)
        );
    }
}