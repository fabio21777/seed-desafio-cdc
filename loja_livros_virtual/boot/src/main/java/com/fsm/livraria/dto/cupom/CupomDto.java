package com.fsm.livraria.dto.cupom;

import com.fsm.livraria.domain.Cupom;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Serdeable
public class CupomDto {

    private UUID uuid;

    private String code;

    private BigDecimal percentageDiscount;


    private LocalDateTime dateValidity;

    public CupomDto() {
    }

    public CupomDto(Cupom cupom) {
        this.uuid = cupom.getUuid();
        this.code = cupom.getCodigo();
        this.percentageDiscount = cupom.getPercentualDesconto();
        this.dateValidity = cupom.getDataValidade();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
