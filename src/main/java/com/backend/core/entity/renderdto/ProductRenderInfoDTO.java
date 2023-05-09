package com.backend.core.entity.renderdto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "product_info_for_ui")
@Getter
@Setter
public class ProductRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    private int id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "name")
    private String name;

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "discount")
    private double discount;

    @Column(name = "brand")
    private String brand;

    @Column(name = "size")
    private String size;

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

    @Column(name = "description")
    private String description;

    @Column(name = "overall_rating")
    private double overallRating;


    public ProductRenderInfoDTO(int id, int productId, String name, String size, double sellingPrice, double discount, String brand, String color, int availableQuantity, String image1, String image2, String image3, String image4) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.size = size;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.brand = brand;
        this.color = color;
        this.availableQuantity = availableQuantity;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
    }

    public ProductRenderInfoDTO(int id, int productId, String name, double sellingPrice, double discount, String brand, String size, String color, int availableQuantity, String image1, String image2, String image3, String image4, String description, double overallRating) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.brand = brand;
        this.size = size;
        this.color = color;
        this.availableQuantity = availableQuantity;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
        this.image4 = image4;
        this.description = description;
        this.overallRating = overallRating;
    }

    public ProductRenderInfoDTO() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRenderInfoDTO that = (ProductRenderInfoDTO) o;
        return id == that.id && productId == that.productId && Double.compare(that.sellingPrice, sellingPrice) == 0 && Double.compare(that.discount, discount) == 0 && availableQuantity == that.availableQuantity && Double.compare(that.overallRating, overallRating) == 0 && Objects.equals(name, that.name) && Objects.equals(brand, that.brand) && Objects.equals(size, that.size) && Objects.equals(color, that.color) && Objects.equals(image1, that.image1) && Objects.equals(image2, that.image2) && Objects.equals(image3, that.image3) && Objects.equals(image4, that.image4) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, name, sellingPrice, discount, brand, size, color, availableQuantity, image1, image2, image3, image4, description, overallRating);
    }

    @Override
    public String toString() {
        return "ProductRenderInfoDTO{" +
                "id=" + id +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                ", brand='" + brand + '\'' +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", description='" + description + '\'' +
                ", overallRating=" + overallRating +
                '}';
    }
}
