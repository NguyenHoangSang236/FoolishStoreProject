package com.backend.core.entities.tableentity;

import com.backend.core.entities.requestdto.product.ProductDetailsRequestDTO;
import com.backend.core.service.CalculationService;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @Column(name = "selling_price")
    private double sellingPrice;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "discount")
    private double discount;

    @Column(name = "brand")
    private String brand;

    @Column(name = "description")
    private String description;

    @Column(name = "length")
    private int length;

    @Column(name = "height")
    private int height;

    @Column(name = "width")
    private int width;

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
        this.sellingPrice = request.getSellingPrice();
        this.originalPrice = request.getOriginalPrice();
        this.brand = request.getBrand().toLowerCase();
        this.discount = request.getDiscount();
        this.description = request.getDescription();
    }
}
