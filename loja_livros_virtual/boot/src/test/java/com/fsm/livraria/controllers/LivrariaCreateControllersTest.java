package com.fsm.livraria.controllers;

import com.fsm.livraria.dto.AutorCreateRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class LivrariaCreateControllersTest {

    @Inject
    RequestSpecification spec;

    @Test
    public  void  testSanity() {
        // Verifica se o contexto do Micronaut está carregado corretamente
        assertNotNull(spec);
    }

    @Test
    public void testCriarAutorComSucesso() {
        AutorCreateRequest request = new AutorCreateRequest(
                "Jorge Amado",
                "jorge"+uuid() +"@exemplo.com",
                "Autor brasileiro de renome"
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.CREATED.getCode())
                .body("name", equalTo("Jorge Amado"))
                .body("email", equalTo(request.email()))
                .body("description", equalTo("Autor brasileiro de renome"))
                .body("createdAt", notNullValue());
    }

    @Test
    public void testEmailNaoPodeSerVazio() {
        AutorCreateRequest request = new AutorCreateRequest(
                "Jorge Amado",
                "",  // Email vazio
                "Autor brasileiro de renome"
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Email não pode ser vazio"));
    }

    @Test
    public void testEmailDeveSerValido() {
        AutorCreateRequest request = new AutorCreateRequest(
                "Jorge Amado",
                "email-invalido",  // Email inválido
                "Autor brasileiro de renome"
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Email inválido"));
    }

    @Test
    public void testNomeNaoPodeSerVazio() {
        AutorCreateRequest request = new AutorCreateRequest(
                "",  // Nome vazio
                "jorge@exemplo.com",
                "Autor brasileiro de renome"
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Nome não pode ser vazio"));
    }

    @Test
    public void testDescricaoNaoPodeExceder400Caracteres() {
        String descricaoLonga = "A".repeat(401); // Cria uma string com 401 caracteres

        AutorCreateRequest request = new AutorCreateRequest(
                "Jorge Amado",
                "jorge@exemplo.com",
                descricaoLonga
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Descrição não pode ter mais de 400 caracteres"));
    }

    @Test
    public void testDescricaoPodeSerNula() {
        AutorCreateRequest request = new AutorCreateRequest(
                "Jorge Amado",
                "jorge"+uuid() +"@exemplo.com",
                ""  // Descrição ser vazia
        );

        spec
                .when()
                .body(request)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.CREATED.getCode());
    }

    @Test
    public void testEmailNaoPodeSerDuplicado() {
        // Primeiro, cria um autor com um email específico
        String email = "autor.teste" + uuid() + "@exemplo.com";

        AutorCreateRequest primeiroAutor = new AutorCreateRequest(
                "Primeiro Autor",
                email,
                "Descrição do primeiro autor"
        );

        // Cria o primeiro autor
        spec
                .when()
                .body(primeiroAutor)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.CREATED.getCode());

        // Tenta criar outro autor com o mesmo email
        AutorCreateRequest segundoAutor = new AutorCreateRequest(
                "Segundo Autor",
                email,  // Mesmo email do primeiro autor
                "Descrição do segundo autor"
        );

        // Verifica se a criação falha com um erro de email duplicado
        spec
                .when()
                .body(segundoAutor)
                .contentType("application/json")
                .post("/api/v1/livraria")
                .then()
                .statusCode(HttpStatus.UNPROCESSABLE_ENTITY.getCode())
                .body("message", containsString("Email já cadastrado"));
    }

    private String  uuid(){
        return java.util.UUID.randomUUID().toString();
    }
}