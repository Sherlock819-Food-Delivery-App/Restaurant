package com.example.Restaurant.service;

import com.example.Restaurant.dto.RestaurantOwnerDTO;
import com.example.Restaurant.model.RestaurantOwner;

import java.util.List;

public interface RestaurantOwnerService {

    RestaurantOwnerDTO createRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO);

    List<RestaurantOwnerDTO> getAllRestaurantOwners();

    RestaurantOwnerDTO getRestaurantOwnerById(Long id);

    RestaurantOwnerDTO updateRestaurantOwner(Long id, RestaurantOwnerDTO restaurantOwnerDTO);

    void deleteRestaurantOwner(Long id);

    boolean isAuthenticated();

    RestaurantOwner getRestaurantOwner();
}
