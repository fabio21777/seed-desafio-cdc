package com.fsm.livraria.controllers;

import com.fsm.UtilsTest;
import com.fsm.livraria.domain.*;
import com.fsm.livraria.dto.compra.CarrinhoItenRequest;
import com.fsm.livraria.dto.compra.CarrinhoRequest;
import com.fsm.livraria.dto.compra.CompraCreateRequest;
import com.fsm.livraria.dto.compra.CompraDto;
import com.fsm.livraria.dto.livro.LivroCreateRequestDto;
import com.fsm.livraria.dto.paisestado.EstadoDto;
import com.fsm.livraria.repositories.*;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fsm.UtilsTest.uuid;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class CompraControllerTest {

    @Inject
    RequestSpecification spec;

    @Inject
    UtilsTest utilsTest;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    PaisRepository paisRepository;

    @Inject
    LivroRepository livroRepository;

    @Inject
    AutorRepository autorRepository;

    @Inject
    CategoriaRepository categoriaRepository;

    private String token;

    private Pais pais;

    private Pais paisSemEstados;

    private List<Estado> estados;

    private List<Livro> livros = new ArrayList<>();

    Autor autor;

    Categoria categoria;

    private static final String PATH = "api/v1/purchase";

    @BeforeEach
    void setUp() {
        if (token != null) {
            return;
        }
        this.token = utilsTest.getToken();
        criarPais();
        criarEstados();
        criarPaisSemEstados();
        criarLivros(3);

    }

    private void criarLivros(int qtdLivros) {
        //
        this.autor = new Autor("email" + uuid() + "@teste.com.br",
                "Nome do Autor" + uuid(),
                "Descrição do autor");

        this.autor = autorRepository.save(autor);

        this.categoria = categoriaRepository.save("Categoria Teste " + uuid());
        Livro livro = null;
        for (int i = 0; i < qtdLivros; i++) {
            livro = new Livro(
                    "Java Efetivo: Programação Prática" + uuid(),
                    "Um guia prático para as melhores práticas em Java",
                    "# Capítulo 1\n\nIntrodução ao Java moderno\n\n# Capítulo 2\n\nBoas práticas de programação",
                    new BigDecimal("129.90"),
                    320,
                    "978-8550804583" + uuid(),
                    LocalDateTime.now().plusDays(30),
                    categoria,
                    autor);

            livro = livroRepository.save(livro);
            livros.add(livro);
        }
    }

    @Test
    void testSanity() {
        // Verifica se o contexto do Micronaut está carregado corretamente
        assertNotNull(spec);
    }

    @Test
    @DisplayName("Deve criar uma compra com sucesso")
    void deveCriarUmaCompraComSucesso() {
        CompraCreateRequest request = criarRequest();
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

        CompraDto compraDto = response.getBody().as(CompraDto.class);
        assertNotNull(compraDto);

        assertEquals(request.getEmail(), compraDto.getEmail());
        assertEquals(request.getFirstName(), compraDto.getFirstName());
        assertEquals(request.getLastName(), compraDto.getLastName());
        assertEquals(request.getDocument(), compraDto.getDocument());
        assertEquals(request.getAddress(), compraDto.getAddress());
        assertEquals(request.getAddressComplement(), compraDto.getAddressComplement());
        assertEquals(request.getCity(), compraDto.getCity());
        assertEquals(request.getCountry(), compraDto.getCountry().getUuid());
        assertEquals(request.getState(), compraDto.getState().getUuid());

        //validar carrinho e itens

        assertNotNull(compraDto.getCart());

        assertEquals(request.getCart().getTotal(), compraDto.getCart().getTotal());
        assertNotNull(compraDto.getCart().getItems());
        assertEquals(request.getCart().getItems().size(), compraDto.getCart().getItems().size());

        //deve ter os mesmos livros
        livros.forEach(livro -> {
            assertTrue(compraDto.getCart().getItems().stream()
                    .anyMatch(item -> item.getLivro().getUuid().equals(livro.getUuid())));
        });


    }

    @Test
    @DisplayName("Deve falhar ao criar compra com email inválido")
    void deveFalharComEmailInvalido() {
        CompraCreateRequest request = criarRequest();
        request.setEmail("email-invalido");

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Email inválido"));
    }

    @Test
    @DisplayName("Deve falhar ao criar compra com CPF/CNPJ inválido")
    void deveFalharComCPFouCNPJInvalido() {
        CompraCreateRequest request = criarRequest();
        request.setDocument("123456");

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("CPF ou CNPJ inválido"));
    }

    @Test
    @DisplayName("Deve ignorar campo estado se país não possui estados")
    void deveIgnorarEstadoQuandoPaisNaoPossuiEstados() {
        CompraCreateRequest request = criarRequest();
        request.setCountry(paisSemEstados.getUuid()); // simule esse UUID
        request.setState(null); // deve ser ignorado

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(201); // sucesso mesmo sem estado
    }


    @Test
    @DisplayName("Deve falhar com CEP em formato inválido")
    void deveFalharComCepInvalido() {
        CompraCreateRequest request = criarRequest();
        request.setZipCode("1234567");

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("CEP inválido"));
    }


    @Test
    @DisplayName("Deve falhar com telefone inválido")
    void deveFalharComTelefoneInvalido() {
        CompraCreateRequest request = criarRequest();
        request.setPhone("123"); // muito curto ou DDD inválido

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Telefone inválido"));
    }

    @Test
    @DisplayName("Deve falhar quando campos obrigatórios não forem preenchidos")
    void deveFalharComCamposObrigatoriosEmBranco() {
        CompraCreateRequest request = new CompraCreateRequest(); // vazio

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("não pode ser vazio"));
    }

    @Test
    @DisplayName("Deve falhar quando o país não for encontrado")
    void deveFalharComPaisNaoEncontrado() {
        CompraCreateRequest request = criarRequest();
        request.setCountry(UUID.fromString(uuid())); // UUID inválido

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(404)
                .body("message", containsString("País não encontrado"));
    }

    @Test
    @DisplayName("Deve falhar quando o estado não for encontrado")
    void deveFalharComEstadoNaoEncontrado() {
        CompraCreateRequest request = criarRequest();
        request.setState(UUID.fromString(uuid())); // UUID inválido

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(404)
                .body("message", containsString("Estado não encontrado"));
    }

    @Test
    @DisplayName("Deve falhar quando o estado não pertence ao país")
    void deveFalharComEstadoNaoPertenceAoPais() {
        CompraCreateRequest request = criarRequest();
        Estado estado = criarEstadoOutroPais();
        request.setState(estado.getUuid()); // Estado de outro país
        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(400)
                .body("message", containsString("tem estados, mas nenhum foi informado"));
    }

    @Test
    @DisplayName("Não deve permitir criar uma compra com um carrinho vazio")
    void naoDevePermitirCarrinhoVazio() {
        CompraCreateRequest request = criarRequest();
        request.setCart(null);

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("O carrinho não pode ser vazio"));
    }

    @Test
    @DisplayName("Não deve permitir criar uma compra com um carrinho sem itens")
    void naoDevePermitirCarrinhoSemItens() {
        CompraCreateRequest request = criarRequest();
        request.getCart().setItems(new HashSet<>());

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("A quantidade no carrinho deve ser no mínimo 1"));
    }

    @Test
    @DisplayName("Não deve permitir criar uma compra 0 de valor total")
    void naoDevePermitirValorTotalZero() {
        CompraCreateRequest request = criarRequest();
        request.getCart().setTotal(BigDecimal.ZERO);

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("Quantidade deve ser maior que 0"));
    }

    @Test
    @DisplayName("Não deve permitir criar uma compra com  o valor total diferente do valor dos livros calculado")
    void naoDevePermitirValorTotalDiferente() {
        CompraCreateRequest request = criarRequest();
        request.getCart().setTotal(new BigDecimal("1000.00"));
        // valor total diferente do calculado

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(400)
                .body("message", containsString("O valor total do carrinho não é igual ao valor dos livros"));
    }

    @Test
    @DisplayName("Deve retornar 404 quando o livro não for encontrado")
    void deveRetornar404QuandoLivroNaoEncontrado() {
        CompraCreateRequest request = criarRequest();
        UUID livroInvalido = UUID.randomUUID(); // UUID inválido

        CarrinhoItenRequest carrinhoItenRequest = request.getCart().getItems().stream().findFirst().get();
        UUID livroUuid = carrinhoItenRequest.getLivro();
        carrinhoItenRequest.setLivro(livroInvalido); // livro inválido
        request.getCart().setTotal(request.getCart().getTotal().subtract(livros.stream().filter( livro -> livro.getUuid().equals(livroUuid)).findFirst().get().getPreco())); // subtrai o valor do livro inválido

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(404)
                .body("message", containsString("Livro não encontrado com UUID: " + livroInvalido));
    }
    @Test
    @DisplayName("Não deve permitir comprar um livro com quantidade menor que 1")
    void naoDevePermitirComprarLivroComQuantidadeMenorQue1() {
        CompraCreateRequest request = criarRequest();
        CarrinhoItenRequest carrinhoItenRequest = request.getCart().getItems().stream().findFirst().get();
        carrinhoItenRequest.setQuatity(0); // quantidade menor que 1

        given()
                .spec(spec)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .contentType("application/json")
                .when()
                .post(PATH)
                .then()
                .statusCode(422)
                .body("message", containsString("A quantidade deve ser maior que 0"));
    }


    // ============================== Métodos auxiliares ==============================

    private void criarPais() {
        pais = new Pais();
        pais.setNome("Brasil" + uuid());
        pais.setSigla("BR");
        pais = paisRepository.save(pais);
    }

    private Pais criarPaisSemEstados() {
        Pais pais = new Pais();
        pais.setNome("País Sem Estados" + uuid());
        pais.setSigla("PS");
        paisSemEstados = paisRepository.save(pais);
        return paisSemEstados;
    }


    private void criarEstados() {
        estados = List.of(
                new Estado("São Paulo" + uuid(), "SP", pais),
                new Estado("Rio de Janeiro" + uuid(), "RJ", pais),
                new Estado("Minas Gerais" + uuid(), "MG", pais)
        );
        estados = estadoRepository.saveAll(estados);
    }

    private Estado criarEstadoOutroPais() {
        Pais outroPais = new Pais();
        outroPais.setNome("Argentina" + uuid());
        outroPais.setSigla("AR");
        outroPais = paisRepository.save(outroPais);

        Estado estado = new Estado("Buenos Aires" + uuid(), "BA", outroPais);
        return estado;
    }

    private CompraCreateRequest criarRequest() {
        CompraCreateRequest request = new CompraCreateRequest();

        request.setEmail("test" + uuid() + "@example.com");
        request.setFirstName("Nome" + uuid());
        request.setLastName("Sobrenome" + uuid());
        request.setDocument("32630145018");
        request.setAddress("Rua Teste, 123");
        request.setAddressComplement("Apto 101");
        request.setCity("São Paulo");
        request.setCountry(pais.getUuid());
        request.setState(estados.getFirst().getUuid());
        request.setPhone("11999887766");
        request.setZipCode("01310-000");
        request.setCart(criarCarrinhoRequest());
        return request;
    }

    private CarrinhoRequest criarCarrinhoRequest() {
        CarrinhoRequest carrinho = new CarrinhoRequest();
        carrinho.setTotal(livros.stream()
                .map(Livro::getPreco)
                .reduce(BigDecimal.ZERO, BigDecimal::add));
        carrinho.setItems(livros.stream()
                .map(livro -> {
                    CarrinhoItenRequest item = new CarrinhoItenRequest();
                    item.setLivro(livro.getUuid());
                    item.setQuatity(1);
                    return item;
                })
                .collect(Collectors.toSet()));
        return carrinho;
    }
}