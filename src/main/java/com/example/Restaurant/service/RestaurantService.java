package com.example.Restaurant.service;


import com.example.Restaurant.dto.RestaurantDTO;

import java.util.List;

public interface RestaurantService {

    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO);

    RestaurantDTO getRestaurantById(Long id);

    List<RestaurantDTO> getAllRestaurants();

    RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO);

    String deleteRestaurant(Long id);
}
