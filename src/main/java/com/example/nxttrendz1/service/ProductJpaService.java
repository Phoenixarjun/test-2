package com.example.nxttrendz1.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.nxttrendz1.repository.*;
import com.example.nxttrendz1.model.*;

@Service
public class ProductJpaService implements ProductRepository {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Product> getProducts() {
        List<Product> productList = productJpaRepository.findAll();
        ArrayList<Product> products = new ArrayList<>(productList);
        return products;
    }

    @Override
    public Product getProductById(int productId) {
        Optional<Product> optionalProduct = productJpaRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @Override
    public Product addProduct(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Product updateProduct(int productId, Product product) {
        Optional<Product> optionalProduct = productJpaRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            if (product.getProductName() != null) {
                existingProduct.setProductName(product.getProductName());
            }
            if (product.getPrice() != 0) {
                existingProduct.setPrice(product.getPrice());
            }
            return productJpaRepository.save(existingProduct);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            productJpaRepository.deleteById(productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "NO_CONTENT");
        }
    }
}
