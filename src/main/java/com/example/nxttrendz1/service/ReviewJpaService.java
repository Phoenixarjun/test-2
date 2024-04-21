package com.example.nxttrendz1.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.example.nxttrendz1.repository.*;
import com.example.nxttrendz1.model.*;

@Service
public class ReviewJpaService implements ReviewRepository {

    @Autowired
    private ReviewJpaRepository reviewJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public ArrayList<Review> getReviews() {
        List<Review> reviewList = reviewJpaRepository.findAll();
        ArrayList<Review> reviews = new ArrayList<>(reviewList);
        return reviews;
    }

    @Override
    public Review addReview(Review review) {
        try {
            Product product = productJpaRepository.findById(review.getProduct().getProductId()).get();
            return reviewJpaRepository.save(review);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong productId");
        }
    }

    @Override
    public Review getReviewById(int reviewId) {
        Optional<Review> optionalReview = reviewJpaRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            return optionalReview.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    @Override
    public Review updateReview(int reviewId, Review review) {
        Optional<Review> optionalReview = reviewJpaRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review existingReview = optionalReview.get();
            if (review.getReviewContent() != null) {
                existingReview.setReviewContent(review.getReviewContent());
            }
            if (review.getRating() != 0) {
                existingReview.setRating(review.getRating());
            }
            if (review.getProduct() != null) {
                try {
                    Product product = productJpaRepository.findById(review.getProduct().getProductId()).get();
                    existingReview.setProduct(product);
                } catch (NoSuchElementException e) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong productId");
                }
            }
            return reviewJpaRepository.save(existingReview);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    @Override
    public void deleteReview(int reviewId) {
        try {
            reviewJpaRepository.deleteById(reviewId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }

    @Override
    public Product getProductOfReview(int reviewId) {
        Optional<Review> optionalReview = reviewJpaRepository.findById(reviewId);
        if (optionalReview.isPresent()) {
            Review review = optionalReview.get();
            return review.getProduct();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }
    }
}
