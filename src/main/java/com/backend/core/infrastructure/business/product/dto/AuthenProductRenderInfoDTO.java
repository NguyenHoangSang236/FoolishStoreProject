package com.backend.core.infrastructure.business.product.dto;

import com.backend.core.infrastructure.business.category.dto.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "product_full_info_for_ui")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenProductRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "size")
    private String size;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "discount")
    private double discount;

    @Column(name = "brand")
    private String brand;

    @Column(name = "color")
    private String color;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "image_1")
    private String image1;

    @Column(name = "image_2")
    private String image2;

    @Column(name = "image_3")
    private String image3;

    @Column(name = "image_4")
    private String image4;

    @Column(name = "overall_rating")
    private int overallRating;

    @Column(name = "description")
    private String description;

    @Transient
    @JsonProperty("categories")
    private List<CategoryDTO> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenProductRenderInfoDTO that = (AuthenProductRenderInfoDTO) o;
        return id == that.id && productId == that.productId && Double.compare(sellingPrice, that.sellingPrice) == 0 && Double.compare(originalPrice, that.originalPrice) == 0 && Double.compare(discount, that.discount) == 0 && availableQuantity == that.availableQuantity && overallRating == that.overallRating && Objects.equals(name, that.name) && Objects.equals(size, that.size) && Objects.equals(brand, that.brand) && Objects.equals(color, that.color) && Objects.equals(image1, that.image1) && Objects.equals(image2, that.image2) && Objects.equals(image3, that.image3) && Objects.equals(image4, that.image4) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, size, sellingPrice, originalPrice, discount, brand, color, availableQuantity, image1, image2, image3, image4, overallRating, description);
    }

    @Override
    public String toString() {
        return "AuthenProductRenderInfoDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", originalPrice=" + originalPrice +
                ", discount=" + discount +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", overallRating=" + overallRating +
                ", description='" + description + '\'' +
                '}';
    }
}
