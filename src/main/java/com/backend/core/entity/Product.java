package com.backend.core.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import com.backend.core.entity.interfaces.PurchaseCalculation;
import com.backend.core.service.CalculationService;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.backend.core.entity.dto.InvoicesWithProducts;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "products")
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable, PurchaseCalculation {
    /**
     *
     */
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


    @Column(name = "Discount")
    private double discount;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "description")
    private String description;

//    @ManyToMany(mappedBy = "products")
//    private List<Invoice> invoice;

    @OneToMany(mappedBy = "product")
    List<InvoicesWithProducts> invoicesWithProducts;


    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Catalog> catalogs;

    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> comments;

    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductManagement> productManagements;

    @OneToMany(mappedBy = "product")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<ProductImagesManagement> productImagesManagement;

    public Product() { }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.sellingPrice, sellingPrice) == 0 && Double.compare(product.originalPrice, originalPrice) == 0 && Double.compare(product.discount, discount) == 0 && name.equals(product.name) && brand.equals(product.brand) && description.equals(product.description) && invoicesWithProducts.equals(product.invoicesWithProducts) && catalogs.equals(product.catalogs) && comments.equals(product.comments) && productManagements.equals(product.productManagements) && productImagesManagement.equals(product.productImagesManagement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, sellingPrice, originalPrice, discount, brand, description, invoicesWithProducts, catalogs, comments, productManagements, productImagesManagement);
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
                ", invoicesWithProducts=" + invoicesWithProducts +
                ", catalogs=" + catalogs +
                ", comments=" + comments +
                ", productManagements=" + productManagements +
                ", productImagesManagement=" + productImagesManagement +
                '}';
    }


    @Override
    public double calculation(CalculationService calculationService) {
        return calculationService.getTotalPriceOfSingleProduct(this, this.sellingPrice, this.discount);
    }
}
