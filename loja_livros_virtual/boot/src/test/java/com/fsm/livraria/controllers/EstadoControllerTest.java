package com.fsm.livraria.controllers;

import com.fsm.UtilsTest;

import com.fsm.livraria.domain.Pais;
import com.fsm.livraria.dto.paisestado.EstadoCreateRequest;
import com.fsm.livraria.dto.paisestado.EstadoDto;
import com.fsm.livraria.repositories.PaisRepository;
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
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class EstadoControllerTest {


    @Inject
    RequestSpecification spec;

    @Inject
    UtilsTest utilsTest;

    @Inject
    PaisRepository paisRepository;

    private String token;

    private Pais pais;

    private static  final String PATH = "api/v1/state";


    @BeforeEach
    void setUp() {
        if(token == null){
            this.token = utilsTest.getToken();
            pais = paisRepository.save(uuid(), uuid().substring(0,2));
        }
    }

    @Test
    void testSanity() {
        // Verifica se o contexto do Micronaut está carregado corretamente
        assertNotNull(spec);
    }

    // Adicione mais testes aqui conforme necessário

    @Test
    @DisplayName("Deve criar um estado com sucesso")
    void testCriarestadoComSucesso() {
        EstadoCreateRequest request = criarRequest();

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

        EstadoDto EstadoDto = response.getBody().as(EstadoDto.class);

        assertNotNull(EstadoDto);
        assertEquals(request.getName(), EstadoDto.getName());
        assertEquals(request.getAbreviation(), EstadoDto.getAbreviation());
        assertNotNull(EstadoDto.getUuid());
        assertEquals(pais.getUuid(), EstadoDto.getCountry().getUuid());
        assertEquals(pais.getNome(), EstadoDto.getCountry().getName());
    }

    @Test
    @DisplayName("Deve retorna erro quando o nome do estado é nulo")
    void testCriarestadoComNomeNulo() {
        EstadoCreateRequest request = criarRequest();
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
    @DisplayName("O valor maximo do nome do estado é 100")
    void testCriarestadoComNomeMaximo() {
        EstadoCreateRequest request = criarRequest();
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
    @DisplayName("Deve retorna erro quando a sigla do estado é nula")
    void testCriarestadoComSiglaNula() {
        EstadoCreateRequest request = criarRequest();
        request.setAbreviation(null);

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("A Sigla não pode ser vazio"));
    }

    @Test
    @DisplayName("O valor maximo da sigla do estado é 2")
    void testCriarestadoComSiglaMaximo() {
        EstadoCreateRequest request = criarRequest();
        request.setAbreviation("AAA");

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("A sigla deve ter no máximo 2 caracteres"));
    }

    @Test
    @DisplayName("Não deve permitir criar dois estadoes com o mesmo nome")
    void testCriarestadoComNomeDuplicado() {
        EstadoCreateRequest request = criarRequest();

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
                .body("message", containsString("Já existe um estado com esse nome"));
    }

    @Test
    @DisplayName("Não deve permitir criar um estado sem um país")
    void testCriarestadoComPaisNulo() {
        EstadoCreateRequest request = criarRequest();
        request.setCountry(null);

        spec
                .when()
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("País não pode ser vazio"));
    }


    private EstadoCreateRequest criarRequest(){
        return new EstadoCreateRequest(
                uuid(),
               gerarSiglaAleatoria(), pais.getUuid()
        );
    }


}