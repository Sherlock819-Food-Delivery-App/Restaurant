package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.RestaurantReviewDTO;
import com.example.Restaurant.model.RestaurantReview;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantReviewMapper {

    RestaurantReviewDTO toDTO(RestaurantReview restaurantReview);

    RestaurantReview toEntity(RestaurantReviewDTO restaurantReviewDTO);

    List<RestaurantReviewDTO> toDTO(List<RestaurantReview> restaurantReviews);

    List<RestaurantReview> toEntity(List<RestaurantReviewDTO> restaurantReviewDTOs);
}

