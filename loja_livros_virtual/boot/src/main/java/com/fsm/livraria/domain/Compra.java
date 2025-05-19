package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import com.fsm.livraria.validation.cpfcnpj.CPFOrCNPJ;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.data.annotation.TypeDef;
import io.micronaut.data.model.DataType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@MappedEntity
public class Compra extends BaseDomain {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String nome;

    @NotBlank
    private String sobrenome;

    @NotBlank
    private String documento;

    @NotBlank
    private String endereco;

    @NotBlank
    private String complemento;

    @NotBlank
    private String cidade;

    @Relation(Relation.Kind.MANY_TO_ONE)
    private Pais pais;

    @Relation(Relation.Kind.MANY_TO_ONE)
    private Estado estado;

    @NotBlank
    private String telefone;

    @NotBlank
    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido")
    private String cep;

    @Relation(value = Relation.Kind.ONE_TO_ONE, mappedBy = "compra", cascade = Relation.Cascade.ALL)
    private Carrinho carrinho;


    @NotBlank
    @TypeDef(type = DataType.STRING)
    private CompraStatus status;

    private BigDecimal valorFinal;

    public Compra() {
    }


    public Compra(@NotBlank(message = "Nome não pode ser vazio") @Size(max = 100, message = "Nome não pode ter mais de 100 caracteres") String firstName, @NotBlank(message = "Sobrenome não pode ser vazio") @Size(max = 100, message = "Sobrenome não pode ter mais de 100 caracteres") String lastName, @NotBlank(message = "Nome não pode ser vazio") @Size(max = 14, message = "CPF ou CNPJ inválido") @CPFOrCNPJ(message = "CPF ou CNPJ inválido") String document, @NotBlank @Email(message = "Email inválido") String email, @NotBlank(message = "Endereço não pode ser vazio") String address, @NotBlank(message = "O complemento não pode ser vazio") String addressComplement, @NotBlank(message = "O bairro não pode ser vazio") String city, Estado byUuidOrElseThrow, Pais byUuidOrElseThrow1, @NotBlank @Size(max = 11, message = "Telefone inválido") String phone, @NotBlank String zipCode,  CompraStatus status) {
        this.nome = firstName;
        this.sobrenome = lastName;
        this.documento = document;
        this.email = email;
        this.endereco = address;
        this.complemento = addressComplement;
        this.cidade = city;
        this.estado = byUuidOrElseThrow;
        this.pais = byUuidOrElseThrow1;
        this.telefone = phone;
        this.cep = zipCode;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public CompraStatus getStatus() {
        return status;
    }

    public void setStatus(CompraStatus status) {
        this.status = status;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }

    public  void calcularValorFinal(CompraCupom cupom) {
        if (cupom != null) {
            this.valorFinal = this.carrinho.getTotal().subtract(cupom.getValorDesconto());
        } else {
            this.valorFinal = this.carrinho.getTotal();
        }
    }
}
