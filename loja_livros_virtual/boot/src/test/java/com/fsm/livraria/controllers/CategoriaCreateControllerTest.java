package com.fsm.livraria.controllers;

import com.fsm.UtilsTest;
import com.fsm.livraria.dto.CategoriaCreateRequest;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static com.fsm.UtilsTest.uuid;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class CategoriaCreateControllerTest {
    @Inject
    RequestSpecification spec;

    @Inject
    UtilsTest utilsTest;

    String token;

    @BeforeEach
    public void setup() {
        if (token != null) {
            return;
        }
        this.token = utilsTest.getToken();
    }

    @Test
    public  void  testSanity() {
        // Verifica se o contexto do Micronaut está carregado corretamente
        assertNotNull(spec);
    }

    @Test
    @DisplayName("verifica se o endpoint de criar categoria está funcionando")
    public void testCreateCategoria() {
        // Cria uma nova categoria
        CategoriaCreateRequest categoria = new CategoriaCreateRequest("Ficção Científica" + uuid());

        // Envia a requisição para criar a categoria
        spec.given()
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(categoria)
                .when()
                .post("api/v1/categories")
                .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("verifica se o endpoint de criar categoria não aceita nome duplicado")
    public void  testCreateCategoriaComNomeDuplicado() {
        // Cria uma nova categoria
        CategoriaCreateRequest categoria = new CategoriaCreateRequest("Ficção Científica" + uuid());

        // Envia a requisição para criar a categoria
        spec.given()
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(categoria)
                .when()
                .post("api/v1/categories")
                .then()
                .statusCode(201);

        // Tenta criar a mesma categoria novamente
        spec.given()
                .contentType("application/json")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(categoria)
                .when()
                .post("api/v1/categories")
                .then()
                .statusCode(422);
    }
}
