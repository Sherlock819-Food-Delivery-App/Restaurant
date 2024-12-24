package com.example.Restaurant.service;

import com.example.Restaurant.dto.RestaurantDTO;
import org.example.exceptions.NoSuchElementExistsException;

import java.util.List;

public interface RestaurantManagementService {
    RestaurantDTO getRestaurant(Long ownerId) throws NoSuchElementExistsException;
    List<RestaurantDTO> getAllRestaurant() throws NoSuchElementExistsException;
    RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO, Long ownerId) throws Exception;
    RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO, Long ownerId) throws Exception;
    void deleteRestaurant(Long ownerId) throws NoSuchElementExistsException;
}
