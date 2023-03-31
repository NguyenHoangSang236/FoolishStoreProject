package com.backend.core.entity.renderdto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Entity
@Table(name = "cart_item_info_for_ui")
@Getter
@Setter
public class CartRenderInfoDTO {
    @Id
    @Column(name = "id", unique = true)
    int id;

    @Column(name = "customer_id")
    int customerId;

    @Column(name = "product_management_id")
    int productManagementId;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "buying_status")
    String buyingStatus;

    @Column(name = "product_id")
    int productId;

    @Column(name = "color")
    String color;

    @Column(name = "size")
    String size;

    @Column(name = "name")
    String name;

    @Column(name = "brand")
    String brand;

    @Column(name = "selling_price")
    double sellingPrice;

    @Column(name = "discount")
    double discount;

    @Column(name = "image_1")
    String image1;


    public CartRenderInfoDTO() {}

    public CartRenderInfoDTO(int id, int customerId, int productManagementId, int quantity, String buyingStatus, int productId, String color, String size, String name, String brand, double sellingPrice, double discount, String image1) {
        this.id = id;
        this.customerId = customerId;
        this.productManagementId = productManagementId;
        this.quantity = quantity;
        this.buyingStatus = buyingStatus;
        this.productId = productId;
        this.color = color;
        this.size = size;
        this.name = name;
        this.brand = brand;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.image1 = image1;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartRenderInfoDTO that = (CartRenderInfoDTO) o;
        return id == that.id && customerId == that.customerId && productManagementId == that.productManagementId && quantity == that.quantity && buyingStatus == that.buyingStatus && productId == that.productId && Double.compare(that.sellingPrice, sellingPrice) == 0 && Double.compare(that.discount, discount) == 0 && color.equals(that.color) && size.equals(that.size) && name.equals(that.name) && brand.equals(that.brand) && image1.equals(that.image1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, productManagementId, quantity, buyingStatus, productId, color, size, name, brand, sellingPrice, discount, image1);
    }

    @Override
    public String toString() {
        return "CartRenderInfo{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", productManagementId=" + productManagementId +
                ", quantity=" + quantity +
                ", buyingStatus=" + buyingStatus +
                ", productId=" + productId +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", discount=" + discount +
                ", image1='" + image1 + '\'' +
                '}';
    }
}
