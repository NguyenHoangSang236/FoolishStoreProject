package com.backend.core.entity.product.key;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImagesManagementPrimaryKeys implements Serializable {
    @Column(name = "product_id", nullable = false)
    int productId;

    @Column(name = "color", nullable = false)
    String color;
}
