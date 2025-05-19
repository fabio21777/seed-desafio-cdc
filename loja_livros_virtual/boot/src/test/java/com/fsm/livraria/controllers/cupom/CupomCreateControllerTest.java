package com.fsm.livraria.controllers.cupom;

import com.fsm.UtilsTest;
import com.fsm.livraria.dto.cupom.CupomCreateRequest;
import com.fsm.livraria.dto.cupom.CupomDto;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fsm.livraria.controllers.cupom.GeradorCodigoLegivel.criarCodigoLegivel;
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
    @DisplayName("criar um novo compom")
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



    //===============================================Métodos auxiliares=========================================================

    private CupomCreateRequest criarRequest() {
        return new CupomCreateRequest(
                criarCodigoLegivel(),
                CreateValorAleatorio0a100(),
                LocalDateTime.now().plusDays(1)
        );
    }

    //valor aletario de 0 a 100
    private BigDecimal CreateValorAleatorio0a100() {
        return BigDecimal.valueOf(Math.random() * 100);
    }
}