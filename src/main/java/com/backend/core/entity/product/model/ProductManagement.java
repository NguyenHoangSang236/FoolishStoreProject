package com.backend.core.entity.product.model;

import com.backend.core.entity.cart.model.Cart;
import com.backend.core.entity.invoice.model.InvoicesWithProducts;
import com.backend.core.usecase.service.CalculationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Builder
@Getter
@Setter
@Table(name = "products_management")
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductManagement implements Serializable {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @JsonIgnore
    @OneToMany(mappedBy = "productManagement")
    List<InvoicesWithProducts> invoicesWithProducts;

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

    @Column(name = "overall_rating")
    private int overallRating;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    @Column(name = "import_date")
    private Date importDate;

    @JsonIgnore
    @OneToMany(mappedBy = "productManagement")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Cart> carts;

    @OneToMany(mappedBy = "productManagement")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductImportManagement> productImportManagements;


    public void setTotalRatingNumber() {
        int total = 0;
        int totalRating = 0;

        if (this.oneStarQuantity > 0) {
            total += this.oneStarQuantity;
            totalRating += this.oneStarQuantity;
        }
        if (this.twoStarQuantity > 0) {
            total += this.twoStarQuantity;
            totalRating += this.twoStarQuantity * 2;
        }
        if (this.threeStarQuantity > 0) {
            total += this.threeStarQuantity;
            totalRating += this.threeStarQuantity * 3;
        }
        if (this.fourStarQuantity > 0) {
            total += this.fourStarQuantity;
            totalRating += this.fourStarQuantity * 4;
        }
        if (this.fiveStarQuantity > 0) {
            total += this.fiveStarQuantity;
            totalRating += this.fiveStarQuantity * 5;
        }

        this.overallRating = totalRating / total;
    }

    public void addRatingStars(int rate) {
        switch (rate) {
            case 1:
                this.oneStarQuantity++;
                break;
            case 2:
                this.twoStarQuantity++;
                break;
            case 3:
                this.threeStarQuantity++;
                break;
            case 4:
                this.fourStarQuantity++;
                break;
            case 5:
                this.fiveStarQuantity++;
                break;
        }
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
        return id == that.id && availableQuantity == that.availableQuantity && soldQuantity == that.soldQuantity && oneStarQuantity == that.oneStarQuantity && twoStarQuantity == that.twoStarQuantity && threeStarQuantity == that.threeStarQuantity && fourStarQuantity == that.fourStarQuantity && fiveStarQuantity == that.fiveStarQuantity && overallRating == that.overallRating && Objects.equals(product, that.product) && Objects.equals(invoicesWithProducts, that.invoicesWithProducts) && Objects.equals(color, that.color) && Objects.equals(size, that.size) && Objects.equals(importDate, that.importDate) && Objects.equals(carts, that.carts) && Objects.equals(productImportManagements, that.productImportManagements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product, invoicesWithProducts, id, color, size, availableQuantity, soldQuantity, oneStarQuantity, twoStarQuantity, threeStarQuantity, fourStarQuantity, fiveStarQuantity, overallRating, importDate, carts, productImportManagements);
    }

    @Override
    public String toString() {
        return "ProductManagement{" +
                "product=" + product +
                ", invoicesWithProducts=" + invoicesWithProducts +
                ", id=" + id +
                ", color='" + color + '\'' +
                ", size='" + size + '\'' +
                ", availableQuantity=" + availableQuantity +
                ", soldQuantity=" + soldQuantity +
                ", oneStarQuantity=" + oneStarQuantity +
                ", twoStarQuantity=" + twoStarQuantity +
                ", threeStarQuantity=" + threeStarQuantity +
                ", fourStarQuantity=" + fourStarQuantity +
                ", fiveStarQuantity=" + fiveStarQuantity +
                ", overallRating=" + overallRating +
                ", importDate=" + importDate +
                ", carts=" + carts +
                ", productImportManagements=" + productImportManagements +
                '}';
    }
}
