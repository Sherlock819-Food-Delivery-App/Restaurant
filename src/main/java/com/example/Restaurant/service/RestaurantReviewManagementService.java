package com.example.Restaurant.service;


import com.example.Restaurant.dto.RestaurantReviewDTO;

import java.util.List;

public interface RestaurantReviewManagementService {
    List<RestaurantReviewDTO> getAllReviewsForRestaurant(Long restaurantId);

    RestaurantReviewDTO getReviewById(Long reviewId);

    RestaurantReviewDTO addReview(Long restaurantId, RestaurantReviewDTO reviewDTO);

    RestaurantReviewDTO updateReview(Long reviewId, RestaurantReviewDTO reviewDTO);

    void deleteReview(Long reviewId);
}

