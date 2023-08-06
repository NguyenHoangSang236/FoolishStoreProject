package com.backend.core.entities.renderdto;

import com.backend.core.entities.embededkey.InvoicesWithProductsPrimaryKeys;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "invoice_with_products_info_for_ui")
@Getter
@Setter
public class InvoiceDetailRenderInfoDTO {
    @EmbeddedId
    InvoicesWithProductsPrimaryKeys id;

    @Column(name = "quantity")
    int quantity;

    @Column(name = "color")
    String color;

    @Column(name = "size")
    String size;

    @Column(name = "one_star_quantity")
    int oneStarQuantity;

    @Column(name = "two_star_quantity")
    int twoStarQuantity;

    @Column(name = "three_star_quantity")
    int threeStarQuantity;

    @Column(name = "four_star_quantity")
    int fourStarQuantity;

    @Column(name = "five_star_quantity")
    int fiveStarQuantity;

    @Column(name = "image_1")
    String image;


    public InvoiceDetailRenderInfoDTO(InvoicesWithProductsPrimaryKeys id, int quantity, String color, String size, int oneStarQuantity, int twoStarQuantity, int threeStarQuantity, int fourStarQuantity, int fiveStarQuantity, String image) {
        this.id = id;
        this.quantity = quantity;
        this.color = color;
        this.size = size;
        this.oneStarQuantity = oneStarQuantity;
        this.twoStarQuantity = twoStarQuantity;
        this.threeStarQuantity = threeStarQuantity;
        this.fourStarQuantity = fourStarQuantity;
        this.fiveStarQuantity = fiveStarQuantity;
        this.image = image;
    }

    public InvoiceDetailRenderInfoDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceDetailRenderInfoDTO that = (InvoiceDetailRenderInfoDTO) o;
        return quantity == that.quantity && oneStarQuantity == that.oneStarQuantity && twoStarQuantity == that.twoStarQuantity && threeStarQuantity == that.threeStarQuantity && fourStarQuantity == that.fourStarQuantity && fiveStarQuantity == that.fiveStarQuantity && Objects.equals(id, that.id) && Objects.equals(color, that.color) && Objects.equals(size, that.size) && Objects.equals(image, that.image);
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, color, size, oneStarQuantity, twoStarQuantity, threeStarQuantity, fourStarQuantity, fiveStarQuantity, image);
    }


    @Override
    public String toString() {
        return "InvoiceDetailRenderInfoDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", oneStarQuantity=" + oneStarQuantity +
                ", twoStarQuantity=" + twoStarQuantity +
                ", threeStarQuantity=" + threeStarQuantity +
                ", fourStarQuantity=" + fourStarQuantity +
                ", fiveStarQuantity=" + fiveStarQuantity +
                ", image='" + image + '\'' +
                '}';
    }
}
