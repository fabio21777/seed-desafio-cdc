package com.fsm.livraria.dto.compra;

import com.fsm.livraria.domain.Compra;
import com.fsm.livraria.domain.Estado;
import com.fsm.livraria.domain.Pais;
import com.fsm.livraria.dto.paisestado.EstadoDto;
import com.fsm.livraria.dto.paisestado.PaisDto;
import com.fsm.livraria.validation.cpfcnpj.CPFOrCNPJ;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Serdeable
public class CompraDto {

    private UUID uuid;

    private String email;

    private String firstName;

    private String lastName;

    private String document;

    private String address;

    private String addressComplement;

    private String city;

    private PaisDto country;

    private EstadoDto state;

    private String phone;

    private String zipCode;

    private CarrinhoDto cart;

    public CompraDto() {
    }

    public CompraDto(Compra compra) {
        uuid = compra.getUuid();
        email = compra.getEmail();
        firstName = compra.getNome();
        lastName = compra.getSobrenome();
        document = compra.getDocumento();
        address = compra.getEndereco();
        addressComplement = compra.getComplemento();
        city = compra.getCidade();
        country = new PaisDto(compra.getPais());
        state = new EstadoDto(compra.getEstado());
        phone = compra.getTelefone();
        zipCode = compra.getCep();
        cart = new CarrinhoDto(compra.getCarrinho());
    }

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

    public PaisDto getCountry() {
        return country;
    }

    public void setCountry(PaisDto country) {
        this.country = country;
    }

    public EstadoDto getState() {
        return state;
    }

    public void setState(EstadoDto state) {
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

    public CarrinhoDto getCart() {
        return cart;
    }

    public void setCart(CarrinhoDto cart) {
        this.cart = cart;
    }
}
