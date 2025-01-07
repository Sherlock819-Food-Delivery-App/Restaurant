package com.example.Restaurant.service;

import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.model.Restaurant;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.utilities.mapper.RestaurantMapper;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagementServiceImpl.class);

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Override
    public RestaurantDTO getRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found for id: " + restaurantId));
        return restaurantMapper.toDTO(restaurant);
    }
}
