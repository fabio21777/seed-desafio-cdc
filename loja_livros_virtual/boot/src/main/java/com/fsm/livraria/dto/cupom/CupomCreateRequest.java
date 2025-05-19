package com.fsm.livraria.dto.cupom;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fsm.livraria.validation.cupom.CupomNotDuplicateCodigo;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Serdeable
public class CupomCreateRequest {

    @NotBlank(message = "O código do cupom deve ser informado")
    @Size(min = 3, max = 64, message = "O código do cupom deve ter entre 3 e 64 caracteres")
    @Pattern(regexp = "^(?=.*[aeiouáàâãéêíóôõúüAEIOUÁÀÂÃÉÊÍÓÔÕÚÜ])(?!.*[bcdfghjklmnpqrstvwxyzçBCDFGHJKLMNPQRSTVWXYZÇ]{4,})([a-záàâãéêíóôõúüçA-ZÁÀÂÃÉÊÍÓÔÕÚÜÇ0-9]+)$"
            , message = "O código do cupom deve ser de fácil leitura")
    @CupomNotDuplicateCodigo(message = "Já existe um cupom com este código")
    private String code;

    @Min(value = 1, message = "O valor do desconto deve ser maior que 0")
    @Max(value = 100, message = "O valor do desconto deve ser menor que 100")
    private BigDecimal percentageDiscount;

    @Future(message = "A data de validade deve ser uma data futura")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateValidity;

    public CupomCreateRequest() {
    }

    public CupomCreateRequest(String code, BigDecimal percentageDiscount, LocalDateTime dateValidity) {
        this.code = code;
        this.percentageDiscount = percentageDiscount;
        this.dateValidity = dateValidity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(BigDecimal percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public LocalDateTime getDateValidity() {
        return dateValidity;
    }

    public void setDateValidity(LocalDateTime dateValidity) {
        this.dateValidity = dateValidity;
    }
}
