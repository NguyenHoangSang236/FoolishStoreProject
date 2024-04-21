package com.backend.core.entity.cart.gateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
public class CartItemFilterDTO implements Serializable {
    @JsonProperty("status")
    String[] status;

    @JsonProperty("brand")
    String brand;

    @JsonProperty("name")
    String name;


    public CartItemFilterDTO(String[] status, String brand, String name) {
        this.status = status;
        this.brand = brand;
        this.name = name;
    }

    public CartItemFilterDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItemFilterDTO that = (CartItemFilterDTO) o;
        return Arrays.equals(status, that.status) && Objects.equals(brand, that.brand) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(brand, name);
        result = 31 * result + Arrays.hashCode(status);
        return result;
    }

    @Override
    public String toString() {
        return "CartItemFilterDTO{" +
                "status=" + Arrays.toString(status) +
                ", brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
