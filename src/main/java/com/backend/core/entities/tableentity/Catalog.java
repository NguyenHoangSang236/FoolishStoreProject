package com.backend.core.entities.tableentity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "catalog")
@DynamicInsert
@DynamicUpdate
public class Catalog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private int id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Image")
    private String image;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "catalogs_with_products",
            joinColumns = @JoinColumn(name = "catalog_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;


    public Catalog() {}

    public Catalog(String name, List<Product> products) {
        super();
        this.name = name;
        this.products = products;
    }

    public Catalog(String name) {
        super();
        this.name = name;
    }

    public Catalog(int id, String name, String image, List<Product> products) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.products = products;
    }

    //    public String catalogNameToString() {
//        return ValueRender.linkToString(this.name);
//    }
}
