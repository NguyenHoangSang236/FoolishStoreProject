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

    @Column(name = "name")
    private String name;

    @Column(name = "selling_price")
    private double sellingPrice;

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


    public ProductRenderInfoDTO(int id, String name, double sellingPrice, double discount, String brand, String color, int availableQuantity, String image1, String image2, String image3, String image4) {
        this.id = id;
        this.name = name;
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

    public ProductRenderInfoDTO() {}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductRenderInfoDTO that = (ProductRenderInfoDTO) o;
        return id == that.id && Double.compare(that.sellingPrice, sellingPrice) == 0 && Double.compare(that.discount, discount) == 0 && availableQuantity == that.availableQuantity && name.equals(that.name) && brand.equals(that.brand) && color.equals(that.color) && Objects.equals(image1, that.image1) && Objects.equals(image2, that.image2) && Objects.equals(image3, that.image3) && Objects.equals(image4, that.image4);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, sellingPrice, discount, brand, color, availableQuantity, image1, image2, image3, image4);
    }

    @Override
    public String toString() {
        return "ProductRenderInfoDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                ", brand='" + brand + '\'' +
                ", color='" + color + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                '}';
    }
}
