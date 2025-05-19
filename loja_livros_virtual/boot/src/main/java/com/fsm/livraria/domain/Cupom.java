package com.fsm.livraria.domain;

import com.fsm.base.model.BaseDomain;
import io.micronaut.data.annotation.MappedEntity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@MappedEntity
public class Cupom extends BaseDomain {

    @NotBlank(message = "O código do cupom não pode ser vazio")
    private String codigo;

    @NotBlank(message = "O percentual de desconto não pode ser vazio")
    private BigDecimal percentualDesconto;

    @NotBlank(message = "A data de validade não pode ser vazia")
    @Future(message = "A data de validade deve ser uma data futura")
    private LocalDateTime dataValidade;

    public Cupom() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public LocalDateTime getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDateTime dataValidade) {
        this.dataValidade = dataValidade;
    }
}
