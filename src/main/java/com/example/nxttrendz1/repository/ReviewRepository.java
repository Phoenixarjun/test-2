package com.example.nxttrendz1.repository;

import com.example.nxttrendz1.model.Review;
import org.springframework.stereotype.Repository;
import com.example.nxttrendz1.model.*;
import java.util.*;

@Repository
public interface ReviewRepository {
    ArrayList<Review> getReviews();

    Review addReview(Review review);

    Review getReviewById(int reviewId);

    Review updateReview(int reviewId, Review review);

    void deleteReview(int reviewId);

    Product getProductOfReview(int reviewId);
}