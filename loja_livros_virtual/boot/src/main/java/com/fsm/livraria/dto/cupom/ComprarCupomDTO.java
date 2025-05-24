package com.fsm.livraria.dto.cupom;


import com.fsm.livraria.domain.CompraCupom;
import io.micronaut.serde.annotation.Serdeable;

import java.math.BigDecimal;

@Serdeable
public class ComprarCupomDTO {

    private CupomDto coupon;

    private BigDecimal discount;


    public ComprarCupomDTO() {
    }

    public ComprarCupomDTO(CompraCupom compraCupom) {
        if (compraCupom == null) {
            return;
        }
        this.coupon =  new CupomDto(compraCupom.getId().getCupom());
        this.discount = compraCupom.getValorDesconto();
    }


    public CupomDto getCoupon() {
        return coupon;
    }

    public void setCoupon(CupomDto coupon) {
        this.coupon = coupon;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ComprarCupomDTO{" +
                "coupon=" + coupon +
                ", discount=" + discount +
                '}';
    }
}
