package com.backend.core.entity.cart.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO implements Serializable {
    @NotEmpty
    @JsonProperty("productId")
    int productId;

    @Nullable
    @JsonProperty("cartId")
    int cartId;

    @NotEmpty(message = "Select color first")
    @JsonProperty("color")
    String color;

    @NotEmpty(message = "Select size first")
    @JsonProperty("size")
    String size;

    @NotEmpty(message = "Input quantity first")
    @JsonProperty("quantity")
    int quantity;

    @JsonProperty("select_status")
    int selectStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDTO that = (CartItemDTO) o;
        return productId == that.productId && cartId == that.cartId && quantity == that.quantity && selectStatus == that.selectStatus && Objects.equals(color, that.color) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, cartId, color, size, quantity, selectStatus);
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "productId=" + productId +
                ", cartId=" + cartId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                ", selectStatus=" + selectStatus +
                '}';
    }
}
