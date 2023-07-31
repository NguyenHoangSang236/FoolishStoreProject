package com.backend.core.entities.tableentity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Setter
@Entity
@Table(name = "product_images_management")
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductImagesManagement {
    @Id
    @Column(name = "product_id")
    private int productId;

    @Id
    @Column(name = "color")
    private String color;

    @Column(name = "Image_1")
    private String image1;

    @Column(name = "Image_2")
    private String image2;

    @Column(name = "Image_3")
    private String image3;

    @Column(name = "Image_4")
    private String image4;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
}
