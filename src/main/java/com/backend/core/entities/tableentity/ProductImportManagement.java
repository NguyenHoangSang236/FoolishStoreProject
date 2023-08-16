package com.backend.core.entities.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.annotation.Nullable;
import java.util.Date;

@Getter
@Setter
@Table(name = "product_import_management")
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductImportManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    int id;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "import_quantity")
    int importQuantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_management_id")
    ProductManagement productManagement;

    @Column(name = "import_date")
    Date importDate;

    @Nullable
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "out_of_stock_date", nullable = true)
    Date outOfStockDate;


    public ProductImportManagement(int importQuantity, ProductManagement productManagement, Date importDate) {
        this.importQuantity = importQuantity;
        this.productManagement = productManagement;
        this.importDate = importDate;
    }
}
