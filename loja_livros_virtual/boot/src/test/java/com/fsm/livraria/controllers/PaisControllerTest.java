package com.fsm.livraria.controllers;

import com.fsm.UtilsTest;
import com.fsm.livraria.dto.paisestado.PaisCreateRequest;
import com.fsm.livraria.dto.paisestado.PaisDto;
import com.fsm.livraria.repositories.AutorRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.fsm.UtilsTest.gerarSiglaAleatoria;
import static com.fsm.UtilsTest.uuid;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class PaisControllerTest {


    @Inject
    RequestSpecification spec;

    @Inject
    UtilsTest utilsTest;

    @Inject
    AutorRepository autorRepository;

    private String token;

    private final static String PATH = "/api/v1/country";


    @BeforeEach
    void setUp() {
        this.token = utilsTest.getToken();
    }

    @Test
    void testSanity() {
        // Verifica se o contexto do Micronaut está carregado corretamente
        assertNotNull(spec);
    }

    // Adicione mais testes aqui conforme necessário

    @Test
    @DisplayName("Deve criar um país com sucesso")
    void testCriarPaisComSucesso() {
        PaisCreateRequest request = criarRequest();

        Response response  = spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201)
                .extract().response();


        assertNotNull(response);

        PaisDto paisDto = response.getBody().as(PaisDto.class);

        assertNotNull(paisDto);
        assertEquals(request.getName(), paisDto.getName());
        assertEquals(request.getAbbreviation(), paisDto.getAbreviation());
        assertNotNull(paisDto.getUuid());
    }

    @Test
    @DisplayName("Deve retorna erro quando o nome do país é nulo")
    void testCriarPaisComNomeNulo() {
        PaisCreateRequest request = criarRequest();
        request.setName(null);

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Nome não pode ser vazio"));
    }

    @Test
    @DisplayName("O valor maximo do nome do país é 100")
    void testCriarPaisComNomeMaximo() {
        PaisCreateRequest request = criarRequest();
        request.setName("A".repeat(101));

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Nome deve ter no máximo 100 caracteres"));
    }

    @Test
    @DisplayName("Deve retorna erro quando a sigla do país é nula")
    void testCriarPaisComSiglaNula() {
        PaisCreateRequest request = criarRequest();
        request.setAbbreviation(null);

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Sigla não pode ser vazio"));
    }

    @Test
    @DisplayName("O valor maximo da sigla do país é 2")
    void testCriarPaisComSiglaMaximo() {
        PaisCreateRequest request = criarRequest();
        request.setAbbreviation("AAA");

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Sigla deve ter no máximo 2 caracteres"));
    }

    @Test
    @DisplayName("Não deve permitir criar dois paises com o mesmo nome")
    void testCriarPaisComNomeDuplicado() {
        PaisCreateRequest request = criarRequest();

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(201);

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Já existe um país com esse nome"));
    }


    private PaisCreateRequest criarRequest(){
        return new PaisCreateRequest(
                uuid(),
               gerarSiglaAleatoria()
        );
    }

}