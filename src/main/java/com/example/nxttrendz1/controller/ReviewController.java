package com.example.nxttrendz1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.nxttrendz1.model.*;
import com.example.nxttrendz1.service.*;

@RestController
public class ReviewController {
    @Autowired
    private ReviewJpaService reviewService;

    @GetMapping("/products/reviews")
    public ArrayList<Review> getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/products/reviews/{reviewId}")
    public Review getReviewById(@PathVariable("reviewId") int reviewId) {
        return reviewService.getReviewById(reviewId);
    }

    @PostMapping("/products/reviews")
    public Review addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/products/reviews/{reviewId}")
    public Review updateReview(@PathVariable("reviewId") int reviewId, @RequestBody Review review) {
        return reviewService.updateReview(reviewId, review);
    }

    @DeleteMapping("/products/reviews/{reviewId}")
    public void deleteReview(@PathVariable("reviewId") int reviewId) {
        reviewService.deleteReview(reviewId);
    }

    @GetMapping("/reviews/{reviewId}/product")
    public Product getProductOfReview(@PathVariable("reviewId") int reviewId) {
        return reviewService.getProductOfReview(reviewId);
    }
}
