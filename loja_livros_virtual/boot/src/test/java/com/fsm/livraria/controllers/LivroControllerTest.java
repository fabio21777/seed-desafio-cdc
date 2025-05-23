package com.fsm.livraria.controllers;

import com.fsm.UtilsTest;
import com.fsm.livraria.domain.Autor;
import com.fsm.livraria.domain.Categoria;
import com.fsm.livraria.dto.livro.LivroCreateRequestDto;
import com.fsm.livraria.dto.livro.LivroDTO;
import com.fsm.livraria.repositories.AutorRepository;
import com.fsm.livraria.repositories.CategoriaRepository;
import com.fsm.livraria.repositories.LivroRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.fsm.UtilsTest.uuid;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class LivroControllerTest {

    public static final String PATH = "api/v1/book";
    @Inject
    RequestSpecification spec;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    AutorRepository autorRepository;

    @Inject
    LivroRepository livroRepository;

    @Inject
    UtilsTest utilsTest;

    String token;

    Autor autor;

    Categoria categoria;


    @BeforeEach
    public void setup() {
        if (token != null) {
            return;
        }
        this.token = utilsTest.getToken();

        // Criando um autor e uma categoria para os testes
        this.autor = new Autor("email"+uuid()+"@teste.com.br",
                "Nome do Autor" + uuid(),
                "Descrição do autor");

        this.autor = autorRepository.save(autor);

        this.categoria =  categoriaRepository.save("Categoria Teste " + uuid());


    }


    @Test
    void testSanity() {
        assertNotNull(spec);
    }

    // Dados reutilizáveis válidos para facilitar os testes
    private LivroCreateRequestDto criarLivroValido() {
        return new LivroCreateRequestDto(
                "Java Efetivo: Programação Prática" + uuid(),
                "Um guia prático para as melhores práticas em Java",
                "# Capítulo 1\n\nIntrodução ao Java moderno\n\n# Capítulo 2\n\nBoas práticas de programação",
                new BigDecimal("129.90"),
                320,
                "978-8550804583" + uuid(),
                LocalDateTime.now().plusDays(30),
                categoria.getUuid(),
                autor.getUuid()
        );
    }

    @Test
    @DisplayName("Deve cadastrar livro com dados válidos")
    void testCadastrarLivroValido() {
        LivroCreateRequestDto livro = criarLivroValido();

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();

        assertNotNull(response);
        String string = response.getBody().asString();
        assertNotNull(string);
    }

    @Test
    @DisplayName("Não deve cadastrar livro com título vazio")
    void testTituloVazio() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setTitle("");  // Definindo título vazio

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O título é obrigatório"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com título duplicado")
    void testTituloDuplicado() {
        // Primeiro, cadastramos um livro válido
        LivroCreateRequestDto livro1 = criarLivroValido();

        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro1)
                .when()
                .post(PATH)
                .then()
                .statusCode(201);

        // Agora tentamos cadastrar outro livro com o mesmo título
        LivroCreateRequestDto livro2 = criarLivroValido();
        livro2.setIsbn("978-8576089162");
        livro2.setTitle(livro1.getTitle());
        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro2)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("já existe um livro com este título"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com resumo vazio")
    void testResumoVazio() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setSummary("");  // Definindo resumo vazio

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O resumo é obrigatório"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com resumo maior que 500 caracteres")
    void testResumoMuitoLongo() {
        LivroCreateRequestDto livro = criarLivroValido();
        // Criando um resumo com mais de 500 caracteres
        livro.setSummary("0123456789".repeat(51));

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O resumo deve ter no máximo 500 caracteres"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com preço abaixo do mínimo")
    void testPrecoMinimo() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setPrice(new BigDecimal("19.99"));  // Preço abaixo do mínimo

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O preço deve ser de no mínimo 20"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com número de páginas abaixo do mínimo")
    void testNumeroPaginasMinimo() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setNumberOfPages(99);  // Abaixo do mínimo de 100

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O livro deve ter no mínimo 100 páginas"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com ISBN vazio")
    void testIsbnVazio() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setIsbn("");  // ISBN vazio

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O ISBN é obrigatório"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com ISBN duplicado")
    void testIsbnDuplicado() {
        // Primeiro, cadastramos um livro válido
        LivroCreateRequestDto livro1 = criarLivroValido();

        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro1)
                .when()
                .post(PATH)
                .then()
                .statusCode(201);

        // Agora tentamos cadastrar outro livro com o mesmo ISBN
        LivroCreateRequestDto livro2 = criarLivroValido();
        // Título diferente, mas mesmo ISBN
        livro2.setTitle("Outro Livro Diferente");
        livro2.setIsbn(livro1.getIsbn());

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro2)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("já existe um livro com este ISBN"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com data de publicação no passado")
    void testDataPublicacaoPassado() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setPublicationDate(LocalDateTime.now().minusDays(1));  // Data no passado

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("A data de publicação deve ser no futuro"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com categoria nula")
    void testCategoriaNula() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setCategory(null);  // Categoria nula

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("A categoria é obrigatória"));
    }

    @Test
    @DisplayName("Não deve cadastrar livro com autor nulo")
    void testAutorNulo() {
        LivroCreateRequestDto livro = criarLivroValido();
        livro.setAuthor(null);  // Autor nulo

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro)
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .extract()
                .response();

        String responseBody = response.getBody().asString();
        assertTrue(responseBody.contains("O autor é obrigatório"));
    }
    @Test
    @DisplayName("Deve retornar os detalhes dos livro cadastrado e deve ter paginação")
    void testListarLivros() {
        // Primeiro, cadastramos um livro válido
        LivroCreateRequestDto livro1 = criarLivroValido();

        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro1)
                .when()
                .post(PATH)
                .then()
                .statusCode(201);

        // Agora tentamos listar os livros cadastrados
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("Deve retornar os livros cadastrados com paginação padrão")
    void testListarLivrosComPaginacaoPadrao() {
        // Cadastrar livros de teste
        List<LivroCreateRequestDto> livrosCadastrados = cadastrarNLivros(5, uuid());

        // Consultar sem especificar parâmetros de paginação (usa valores padrão)
        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .body("content", hasSize(greaterThanOrEqualTo(5)))
                .body("content[0].title", notNullValue())
                .body("content[0].uuid", notNullValue())
                .body("totalSize", greaterThanOrEqualTo(5))
                .extract()
                .response();
    }

    @Test
    @DisplayName("Deve retornar livros com paginação personalizada")
    void testListarLivrosComPaginacaoPersonalizada() {
        // Cadastrar livros de teste
        cadastrarNLivros(10, uuid());

        // Listar com tamanho de página 3
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("size", 3)
                .queryParam("page", 0)
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .body("content", hasSize(3))
                .extract()
                .response();

        // Agora vamos para a página 1 (segunda página)
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("size", 3)
                .queryParam("page", 1)
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .body("content", hasSize(3));
    }

    @Test
    @DisplayName("Deve ordenar livros por título em ordem ascendente")
    void testListarLivrosOrdenadosPorTituloAsc() {
        // Cadastrar livros com títulos em ordem não alfabética
        String uuid = uuid();
        List<String> titulos = List.of("Zebra" + uuid, "Cachorro" + uuid, "Abelha" + uuid, "Macaco" + uuid, "Tigre" + uuid);
        cadastrarLivrosComTitulos(titulos);

        // Listar com ordenação ascendente por título
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("sort", "titulo,asc")
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();

    }

    @Test
    @DisplayName("Deve ordenar livros por título em ordem descendente")
    void testListarLivrosOrdenadosPorTituloDesc() {
        // Cadastrar livros com títulos em ordem não alfabética
        String uuid = uuid();
        List<String> titulos = List.of("Zebra" + uuid, "Cachorro" + uuid, "Abelha" + uuid, "Macaco" + uuid, "Tigre" + uuid);
        cadastrarLivrosComTitulos(titulos);

        // Listar com ordenação descendente por título
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("sort", "titulo,desc")
                .when()
                .get(PATH)
                .then()
                .statusCode(200)
                .extract()
                .response();
    }

    @Test
    @DisplayName("Deve retornar detalhes do livro cadastrado")
    void testDetalhesLivroCadastrado() {
        // Primeiro, cadastramos um livro válido
        LivroCreateRequestDto livro1 = criarLivroValido();

        var response = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .body(livro1)
                .when()
                .post(PATH)
                .then()
                .statusCode(201)
                .extract()
                .response();

        LivroDTO livroDto = response.as(LivroDTO.class);

        // Agora tentamos obter os detalhes do livro cadastrado
        var responseUuid  = spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get( PATH + "/" + livroDto.getUuid())
                .then()
                .statusCode(200)
                .body("title", equalTo(livro1.getTitle()))
                .body("uuid", equalTo(livroDto.getUuid().toString()))
                .extract()
                .response();
        //asserts dos detalhes do livro
        LivroDTO livroDetalhes = responseUuid.as(LivroDTO.class);

        assertEquals(livroDto.getTitle(), livroDetalhes.getTitle());
        assertEquals(livroDto.getSummary(), livroDetalhes.getSummary());
        assertEquals(livroDto.getContent(), livroDetalhes.getContent());
        assertEquals(livroDto.getPrice(), livroDetalhes.getPrice());
        assertEquals(livroDto.getNumberOfPages(), livroDetalhes.getNumberOfPages());
        assertEquals(livroDto.getIsbn(), livroDetalhes.getIsbn());
        assertEquals(livroDto.getPublicationDate(), livroDetalhes.getPublicationDate());

        //detalhes do autor
        assertEquals(livroDto.getAuthor().getName(), livroDetalhes.getAuthor().getName());
        assertEquals(livroDto.getAuthor().getEmail(), livroDetalhes.getAuthor().getEmail());
        assertEquals(livroDto.getAuthor().getDescription(), livroDetalhes.getAuthor().getDescription());

        //detalhes da categoria
        assertEquals(livroDto.getCategory().getName(), livroDetalhes.getCategory().getName());
    }

    @Test
    @DisplayName("Deve retornar erro ao tentar acessar livro não cadastrado")
    void testDetalhesLivroNaoCadastrado() {
        // UUID inválido
        String uuidInvalido = "123e4567-e89b-12d3-a456-426614174000";

        // Tentar obter detalhes do livro com UUID inválido
        spec
                .given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .when()
                .get(PATH + "/" + uuidInvalido)
                .then()
                .statusCode(404)
                .body("message", equalTo("Livro não encontrado"));
    }



    private List<LivroCreateRequestDto> cadastrarNLivros(int quantidade, String uuid) {
        List<LivroCreateRequestDto> livros = new ArrayList<>();

        for (int i = 0; i < quantidade; i++) {
            LivroCreateRequestDto livro = criarLivroValido();
            livro.setTitle("Livro Teste " + uuid + i);

            spec
                    .given()
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(livro)
                    .when()
                    .post(PATH)
                    .then()
                    .statusCode(201);

            livros.add(livro);
        }

        return livros;
    }

    private void cadastrarLivrosComTitulos(List<String> titulos) {
        for (String titulo : titulos) {
            LivroCreateRequestDto livro = criarLivroValido();
            livro.setTitle(titulo);

            spec
                    .given()
                    .contentType("application/json")
                    .header("Authorization", "Bearer " + token)
                    .body(livro)
                    .when()
                    .post(PATH)
                    .then()
                    .statusCode(201);
        }
    }


}