package com.backend.core.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "products_management")
@Entity
@DynamicInsert
@DynamicUpdate
public class ProductManagement {
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

    public void addQuantity(int quant) {
        this.availableQuantity += quant;
    }
}
