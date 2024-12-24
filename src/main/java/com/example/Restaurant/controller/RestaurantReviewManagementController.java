package com.example.Restaurant.controller;

import com.example.Restaurant.dto.RestaurantReviewDTO;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.service.RestaurantReviewManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurant-management/reviews")
public class RestaurantReviewManagementController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantReviewManagementController.class);

    @Autowired
    private RestaurantReviewManagementService reviewService;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping
    public ResponseEntity<List<RestaurantReviewDTO>> getAllReviews() {
        Long restaurantId = getRestaurantIdFromAuth();
        logger.info("Fetching all reviews for restaurant: {}", restaurantId);
        return ResponseEntity.ok(reviewService.getAllReviewsForRestaurant(restaurantId));
    }

    private Long getRestaurantIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // fetch username
        return restaurantRepo.findByOwnerId(restaurantOwnerRepo.findByEmail(username).getId()).orElseThrow().getId(); // fetch user based on JWT token details
    }
}

