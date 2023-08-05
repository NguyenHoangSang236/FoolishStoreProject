package com.backend.core.entities.requestdto.cart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class CartCheckoutDTO {
    @JsonProperty("subtotal")
    double subtotal;

    @JsonProperty("total")
    double total;

    @JsonProperty("shippingFee")
    double shippingFee;


    public CartCheckoutDTO(double subtotal, double total, double shippingFee) {
        this.subtotal = subtotal;
        this.total = total;
        this.shippingFee = shippingFee;
    }

    public CartCheckoutDTO() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartCheckoutDTO that = (CartCheckoutDTO) o;
        return Double.compare(that.subtotal, subtotal) == 0 && Double.compare(that.total, total) == 0 && Double.compare(that.shippingFee, shippingFee) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subtotal, total, shippingFee);
    }

    @Override
    public String toString() {
        return "CartCheckoutDTO{" +
                "subtotal=" + subtotal +
                ", shippingFee=" + shippingFee +
                ", total=" + total +
                '}';
    }
}
