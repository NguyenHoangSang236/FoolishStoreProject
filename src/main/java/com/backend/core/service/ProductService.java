package com.backend.core.service;

import com.backend.core.entity.tableentity.Product;
import com.backend.core.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    //create or update a Product
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    //delete a Product by ID
    public void deleteProductById(int id) {
        productRepo.deleteById(id);
    }

    //get all products
    public List<Product> getAllProducts() {
        return productRepo.getAllProducts();
    }

    //get a product by ID
    public Product getProductById(int id) {
        return productRepo.getProductById(id);
    }

    //get 8 best seller products
    public List<Product> getBestSellerProducts() {
        return productRepo.get8BestSellerProducts();
    }

    //get new arrival products
    public List<Product> getNewArrivalProducts() {
        return productRepo.get8NewArrivalProducts();
    }

    //get hot discount products
    public  List<Product> getHotDiscountProducts() {
        return  productRepo.get8HotDiscountProducts();
    }

    //get products by name
    public List<Product> getProductsByName(String productName) {
        return productRepo.getProductsByName(productName);
    }

    //get products by brand
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.getProductsByBrand(brand);
    }


}
