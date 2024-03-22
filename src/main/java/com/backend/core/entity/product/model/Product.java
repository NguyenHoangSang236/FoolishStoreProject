package com.backend.core.entity.product.model;

import com.backend.core.entity.category.model.Catalog;
import com.backend.core.entity.comment.model.Comment;
import com.backend.core.entity.product.gateway.ProductDetailsRequestDTO;
import com.backend.core.usecase.service.CalculationService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = -3612182239388257218L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Min(value = 1, message = "Selling must be greater than 0")
    @Column(name = "selling_price")
    private double sellingPrice;

    @Min(value = 1, message = "Original must be greater than 0")
    @Column(name = "original_price")
    private double originalPrice;

    @Min(value = 0, message = "Discount must be from 0 to 100")
    @Max(100)
    @Column(name = "discount")
    private double discount;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Min(value = 10, message = "Length must be greater than 10 millimeter")
    @Column(name = "length")
    private int length;

    @Min(value = 10, message = "Length must be greater than 10 millimeter")
    @Column(name = "height")
    private int height;

    @Min(value = 10, message = "Length must be greater than 10 millimeter")
    @Column(name = "width")
    private int width;

    @Min(value = 1, message = "Length must be greater than 1 gram")
    @Column(name = "weight")
    private int weight;

    @JsonIgnore
    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Catalog> catalogs;

    @JsonBackReference
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductManagement> productManagements;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductImagesManagement> productImagesManagement;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.sellingPrice, sellingPrice) == 0 && Double.compare(product.originalPrice, originalPrice) == 0 && Double.compare(product.discount, discount) == 0 && name.equals(product.name) && brand.equals(product.brand) && description.equals(product.description) && catalogs.equals(product.catalogs) && comments.equals(product.comments) && productManagements.equals(product.productManagements) && productImagesManagement.equals(product.productImagesManagement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sellingPrice, originalPrice, discount, brand, description, catalogs, comments, productManagements, productImagesManagement);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", originalPrice=" + originalPrice +
                ", discount=" + discount +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", catalogs=" + catalogs +
                ", comments=" + comments +
                ", productManagements=" + productManagements +
                ", productImagesManagement=" + productImagesManagement +
                '}';
    }


    public double calculation(CalculationService calculationService) {
        return calculationService.getTotalPriceOfSingleProduct(this.sellingPrice, this.discount);
    }


    public void getProductFromProductDetailsRequest(ProductDetailsRequestDTO request) {
        if (request.getId() > 0) {
            this.id = request.getId();
        }
        this.name = request.getName();
        this.height = request.getHeight();
        this.weight = request.getWeight();
        this.width = request.getWidth();
        this.length = request.getLength();
        this.sellingPrice = request.getSellingPrice();
        this.originalPrice = request.getOriginalPrice();
        this.brand = request.getBrand().toLowerCase();
        this.discount = request.getDiscount();
        this.description = request.getDescription();
    }
}
