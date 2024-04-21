package com.example.nxttrendz1.repository;

import com.example.nxttrendz1.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductRepository {
    ArrayList<Product> getProducts();

    Product getProductById(int productId);

    Product addProduct(Product product);

    Product updateProduct(int productId, Product product);

    void deleteProduct(int productId);
}