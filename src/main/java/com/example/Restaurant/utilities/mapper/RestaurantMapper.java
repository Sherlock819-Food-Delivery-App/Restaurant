package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.model.Restaurant;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuMapper.class, RestaurantReviewMapper.class, WorkingHourMapper.class})
public interface RestaurantMapper {

    RestaurantDTO toDTO(Restaurant restaurant);

    Restaurant toEntity(RestaurantDTO restaurantDTO);

    List<RestaurantDTO> toDTO(List<Restaurant> restaurants);

    List<Restaurant> toEntity(List<RestaurantDTO> restaurantDTOs);

    @AfterMapping
    default void setMenuAndReviewInChildren(@MappingTarget Restaurant restaurant) {
        if (restaurant.getMenu() != null) {
            restaurant.getMenu().setRestaurant(restaurant);
        }
        if (restaurant.getReviews() != null) {
            restaurant.getReviews().forEach(review -> review.setRestaurant(restaurant));
        }
    }

    @AfterMapping
    default void setRestaurantIdInChildDTOs(@MappingTarget RestaurantDTO restaurantDTO, Restaurant restaurant) {
        if (restaurantDTO.getMenu() != null) {
            restaurantDTO.getMenu().setRestaurantId(restaurant.getId());
        }
        if (restaurantDTO.getReviews() != null) {
            restaurantDTO.getReviews().forEach(reviewDTO -> reviewDTO.setRestaurantId(restaurant.getId()));
        }
    }
}
