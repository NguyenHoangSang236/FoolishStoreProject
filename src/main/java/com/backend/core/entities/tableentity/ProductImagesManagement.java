package com.backend.core.entities.tableentity;

import com.backend.core.entities.embededkey.ProductImagesManagementPrimaryKeys;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.lang.reflect.Field;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "product_images_management")
@DynamicInsert
@DynamicUpdate
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class ProductImagesManagement {
    @EmbeddedId
    ProductImagesManagementPrimaryKeys id;

    @JsonIgnore
    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "Image_1")
    private String image1;

    @Column(name = "Image_2")
    private String image2;

    @Column(name = "Image_3")
    private String image3;

    @Column(name = "Image_4")
    private String image4;


    public void getImagesFromList(String[] imageUrlArr) {
        final Field[] fields = ProductImagesManagement.class.getDeclaredFields();

        for (int i = 0; i < imageUrlArr.length; i++) {
            fields[i + 2].setAccessible(true);

            try {
                fields[i + 2].set(this, imageUrlArr[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImagesManagement that = (ProductImagesManagement) o;
        return Objects.equals(id, that.id) && Objects.equals(image1, that.image1) && Objects.equals(image2, that.image2) && Objects.equals(image3, that.image3) && Objects.equals(image4, that.image4) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image1, image2, image3, image4, product);
    }

    @Override
    public String toString() {
        return "ProductImagesManagement{" +
                "id=" + id +
                ", image1='" + image1 + '\'' +
                ", image2='" + image2 + '\'' +
                ", image3='" + image3 + '\'' +
                ", image4='" + image4 + '\'' +
                ", product=" + product +
                '}';
    }
}
