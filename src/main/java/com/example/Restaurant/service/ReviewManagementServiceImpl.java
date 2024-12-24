package com.example.Restaurant.service;


import com.example.Restaurant.dto.RestaurantReviewDTO;
import com.example.Restaurant.model.RestaurantReview;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.repo.RestaurantReviewRepo;
import com.example.Restaurant.utilities.mapper.RestaurantReviewMapper;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewManagementServiceImpl implements RestaurantReviewManagementService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewManagementServiceImpl.class);

    @Autowired
    private RestaurantReviewRepo reviewRepo;

    @Autowired
    private RestaurantReviewMapper reviewMapper;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public List<RestaurantReviewDTO> getAllReviewsForRestaurant(Long restaurantId) {
        List<RestaurantReview> reviews = reviewRepo.findAllByRestaurantId(restaurantId);
        logger.info("Retrieved {} reviews for restaurant with ID: {}", reviews.size(), restaurantId);
        return reviews.stream().map(reviewMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public RestaurantReviewDTO getReviewById(Long reviewId) {
        RestaurantReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementExistsException("Review not found with id: " + reviewId));
        logger.info("Retrieved review with ID: {}", reviewId);
        return reviewMapper.toDTO(review);
    }

    @Override
    public RestaurantReviewDTO addReview(Long restaurantId, RestaurantReviewDTO reviewDTO) {
        RestaurantReview review = reviewMapper.toEntity(reviewDTO);
        review.setRestaurant(restaurantRepo.findById(restaurantId).orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found with id: " + restaurantId)));
        RestaurantReview savedReview = reviewRepo.save(review);
        logger.info("Added new review with ID: {} for restaurant with ID: {}", savedReview.getId(), restaurantId);
        return reviewMapper.toDTO(savedReview);
    }

    @Override
    public RestaurantReviewDTO updateReview(Long reviewId, RestaurantReviewDTO reviewDTO) {
        RestaurantReview review = reviewRepo.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementExistsException("Review not found with id: " + reviewId));

        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        RestaurantReview updatedReview = reviewRepo.save(review);
        logger.info("Updated review with ID: {}", reviewId);
        return reviewMapper.toDTO(updatedReview);
    }

    @Override
    public void deleteReview(Long reviewId) {
        if (!reviewRepo.existsById(reviewId)) {
            throw new NoSuchElementExistsException("Review not found with id: " + reviewId);
        }
        reviewRepo.deleteById(reviewId);
        logger.info("Deleted review with ID: {}", reviewId);
    }
}
