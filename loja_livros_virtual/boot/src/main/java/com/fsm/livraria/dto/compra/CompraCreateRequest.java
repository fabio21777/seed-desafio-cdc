package com.fsm.livraria.dto.compra;

import com.fsm.exceptions.exception.ServiceError;
import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.CompraStatus;
import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.domain.Pais;
import com.fsm.livraria.repositories.*;
import com.fsm.livraria.validation.cpfcnpj.CPFOrCNPJ;
import io.micronaut.serde.annotation.Serdeable;

import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Serdeable
public class CompraCreateRequest {

    private UUID uuid;

    @NotBlank
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres")
    private String firstName;

    @NotBlank(message = "Sobrenome não pode ser vazio")
    @Size(max = 100, message = "Sobrenome não pode ter mais de 100 caracteres")
    private String lastName;

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 14, message = "CPF ou CNPJ inválido")
    @CPFOrCNPJ(message = "CPF ou CNPJ inválido")
    private String document;

    @NotBlank(message = "Endereço não pode ser vazio")
    private String address;

    @NotBlank(message = "O complemento não pode ser vazio")
    private String addressComplement;

    @NotBlank(message = "O bairro não pode ser vazio")
    private String city;

    private UUID country;

    private UUID state;

    @NotBlank
    @Size(max = 11, min = 11, message = "Telefone inválido")
    private String phone;

    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String zipCode;

    @NotNull(message = "O carrinho não pode ser vazio")
    @Valid
    private CarrinhoRequest cart;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressComplement() {
        return addressComplement;
    }

    public void setAddressComplement(String addressComplement) {
        this.addressComplement = addressComplement;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UUID getCountry() {
        return country;
    }

    public void setCountry(UUID country) {
        this.country = country;
    }

    public UUID getState() {
        return state;
    }

    public void setState(UUID state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public CarrinhoRequest getCart() {
        return cart;
    }

    public void setCart(CarrinhoRequest cart) {
        this.cart = cart;
    }

    public Compra toEntity(EstadoRepository estadoRepository, PaisRepository paisRepository, LivroRepository livroRepository) {
        // Busca o país relacionado
        Pais pais = paisRepository.findByUuidOrElseThrow(country);
        Estado estado = null;

        // Validações de estado
        validarEstadoParaPais(estadoRepository, pais, state);

        // Carrega o estado se foi informado
        if (state != null) {
            estado = estadoRepository.findByUuidOrElseThrow(state);
            validarEstadoPertenceAoPais(pais, estado);
        }
        // Cria e retorna a nova entidade de Compra
        Compra compra = new Compra(
                this.firstName,
                this.lastName,
                this.document,
                this.email,
                this.address,
                this.addressComplement,
                this.city,
                estado,
                pais,
                this.phone,
                this.zipCode,
                CompraStatus.INICIADA
        );

        // Adiciona o carrinho à compra
        compra.setCarrinho(cart.toEntity(livroRepository, compra));

        return compra;
    }

    /**
     * Valida se o estado é necessário para o país informado
     */
    private void validarEstadoParaPais(EstadoRepository estadoRepository, Pais pais, UUID stateUuid) {
        // Se o país tem estados cadastrados mas nenhum estado foi informado
        if (pais.temEstados(estadoRepository) && stateUuid == null) {
            throw new ServiceError("O país " + pais.getNome() + " tem estados, mas nenhum foi informado");
        }
    }

    /**
     * Valida se o estado informado pertence ao país
     */
    private void validarEstadoPertenceAoPais(Pais pais, Estado estado) {
        // Verifica se o estado NÃO pertence ao país
        if (!pais.possuiEstado(estado)) {
            throw new ServiceError("O estado " + estado.getNome() + " não pertence ao país " + pais.getNome());
        }
    }
}
