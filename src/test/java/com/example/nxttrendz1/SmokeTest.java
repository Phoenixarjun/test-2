package com.example.nxttrendz1;

import com.example.nxttrendz1.controller.ProductController;
import com.example.nxttrendz1.controller.ReviewController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private ProductController productController;

    @Autowired
    private ReviewController reviewController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(productController).isNotNull();
        assertThat(reviewController).isNotNull();
    }
}
