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

import java.util.List;

@Service
public class RestaurantManagementServiceImpl implements RestaurantManagementService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagementServiceImpl.class);

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Override
    public RestaurantDTO getRestaurant(Long ownerId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found for owner id: " + ownerId));
        return restaurantMapper.toDTO(restaurant);
    }

    @Override
    public List<RestaurantDTO> getAllRestaurant() {
        return restaurantRepository.findAll().stream().map(restaurantMapper::toDTO).toList();
    }

    @Override
    public RestaurantDTO updateRestaurant(RestaurantDTO restaurantDTO, Long ownerId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found for owner id: " + ownerId));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setCity(restaurantDTO.getCity());
        restaurant.setEmail(restaurantDTO.getEmail());
        restaurant.setDescription(restaurantDTO.getDescription());
        restaurant.setLatitude(restaurantDTO.getLatitude());
        restaurant.setLongitude(restaurantDTO.getLongitude());
        restaurant.setMobile(restaurantDTO.getMobile());
        restaurant.setStatus(restaurantDTO.getStatus());

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(updatedRestaurant);
    }

    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO, Long ownerId) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant.setOwner(restaurantOwnerRepo.findById(ownerId).orElseThrow(() -> new NoSuchElementExistsException("Owner not found for id: " + ownerId)));
        Restaurant createdRestaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDTO(createdRestaurant);
    }

    @Override
    public void deleteRestaurant(Long ownerId) {
        Restaurant restaurant = restaurantRepository.findByOwnerId(ownerId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found for owner id: " + ownerId));
        restaurantRepository.delete(restaurant);
        logger.info("Deleted restaurant for owner id: {}", ownerId);
    }
}

