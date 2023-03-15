package com.backend.core.entity.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Table(name = "product_import_management")
@Entity
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductImportManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "import_date")
    private Date importDate;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "import_quantity")
    int importQuantity;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "out_of_stock_date", nullable = true)
    private Date outOfStockDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_management_id")
    ProductManagement productManagement;


}
