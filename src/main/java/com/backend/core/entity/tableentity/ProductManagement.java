package com.backend.core.entity.tableentity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.backend.core.entity.dto.InvoicesWithProducts;
import com.backend.core.service.CalculationService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "products_management")
@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductManagement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "available_quantity")
    private int availableQuantity;

    @Column(name = "sold_quantity")
    private int soldQuantity;

    @Column(name = "One_star_quantity")
    private int oneStarQuantity;

    @Column(name = "Two_star_quantity")
    private int twoStarQuantity;

    @Column(name = "Three_star_quantity")
    private int threeStarQuantity;

    @Column(name = "Four_star_quantity")
    private int fourStarQuantity;

    @Column(name = "Five_star_quantity")
    private int fiveStarQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @JsonIgnore
    @OneToMany(mappedBy = "productManagement")
    List<InvoicesWithProducts> invoicesWithProducts;

    @JsonIgnore
    @OneToMany(mappedBy = "productManagement", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cart> carts;

    @OneToMany(mappedBy = "productManagement")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductImportManagement> productImportManagements;


    public ProductManagement() {}


    public int getTotalRatingNumber() {
        int total = 0;

        if(this.oneStarQuantity != 0) {
            total += this.oneStarQuantity;
        }
        if(this.twoStarQuantity != 0) {
            total += this.twoStarQuantity;
        }
        if(this.threeStarQuantity != 0) {
            total += this.threeStarQuantity;
        }
        if(this.fourStarQuantity != 0) {
            total += this.fourStarQuantity;
        }
        if(this.fiveStarQuantity != 0) {
            total += this.fiveStarQuantity;
        }

        return total;
    }

    public void addQuantity(String type, int quant) {
        switch (type) {
            case "AVAILABLE_QUANTITY" -> this.availableQuantity += quant;
            case "SOLD_QUANTITY" -> this.soldQuantity += quant;
        }
    }

    public void subtractQuantity(String type, int quant) {
        switch (type) {
            case "AVAILABLE_QUANTITY" -> this.availableQuantity -= quant;
            case "SOLD_QUANTITY" -> this.soldQuantity -= quant;
        }
    }


    public double calculation(CalculationService calculationService) {
        return calculationService.getTotalPriceOfSingleProduct(this.product.getSellingPrice(), this.product.getDiscount());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductManagement that = (ProductManagement) o;
        return id == that.id && availableQuantity == that.availableQuantity && soldQuantity == that.soldQuantity && oneStarQuantity == that.oneStarQuantity && twoStarQuantity == that.twoStarQuantity && threeStarQuantity == that.threeStarQuantity && fourStarQuantity == that.fourStarQuantity && fiveStarQuantity == that.fiveStarQuantity && color.equals(that.color) && size.equals(that.size) && product.equals(that.product) && Objects.equals(carts, that.carts) && productImportManagements.equals(that.productImportManagements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, size, availableQuantity, soldQuantity, oneStarQuantity, twoStarQuantity, threeStarQuantity, fourStarQuantity, fiveStarQuantity, product, carts, productImportManagements);
    }

    @Override
    public String toString() {
        return "ProductManagement{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", soldQuantity=" + soldQuantity +
                ", oneStarQuantity=" + oneStarQuantity +
                ", twoStarQuantity=" + twoStarQuantity +
                ", threeStarQuantity=" + threeStarQuantity +
                ", fourStarQuantity=" + fourStarQuantity +
                ", fiveStarQuantity=" + fiveStarQuantity +
                ", product=" + product +
                ", carts=" + carts +
                ", productImportManagements=" + productImportManagements +
                '}';
    }
}
