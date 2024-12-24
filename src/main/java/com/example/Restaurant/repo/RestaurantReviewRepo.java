package com.example.Restaurant.repo;

import com.example.Restaurant.model.RestaurantReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantReviewRepo extends JpaRepository<RestaurantReview, Long> {

    List<RestaurantReview> findAllByRestaurantId(Long restaurantId);
}
