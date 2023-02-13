package com.backend.core.controller;

import com.backend.core.entity.Product;
import com.backend.core.entity.dto.ApiResponse;
import com.backend.core.repository.ProductRepository;
import com.backend.core.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopPage {
    @Autowired
    private ProductService productService;

    @GetMapping("/allproducts")
    public ApiResponse getAllProducts() {
        List<Product> allProductsList = new ArrayList<Product>();
        String status = "failed";

        try {
            allProductsList = productService.getAllProducts();
            if(allProductsList != null) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, allProductsList);
    }

    @GetMapping("/product_id={productId}")
    public ApiResponse getProductById(@PathVariable(value = "productId") int productId) {
        Product product = new Product();
        String status = "failed";

        try {
            product = productService.getProductById(productId);
            if(product.getName() != null) {
                status = "success";
            }
        }
        catch (Exception e) {
            status = "failed";
            e.printStackTrace();
        }

        return new ApiResponse(status, product);
    }


}
