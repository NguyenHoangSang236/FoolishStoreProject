package com.backend.core.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class CartItemDTO {
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


    public CartItemDTO(int productId, String color, String size, int quantity) {
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public CartItemDTO(int productId, int cartId, String color, String size, int quantity) {
        this.productId = productId;
        this.cartId = cartId;
        this.color = color;
        this.size = size;
        this.quantity = quantity;
    }

    public CartItemDTO() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDTO that = (CartItemDTO) o;
        return productId == that.productId && cartId == that.cartId && quantity == that.quantity && color.equals(that.color) && size.equals(that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, cartId, color, size, quantity);
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "productId=" + productId +
                ", cartId=" + cartId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
