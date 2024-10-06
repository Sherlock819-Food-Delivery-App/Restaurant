package com.example.Restaurant.service;

import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.model.Restaurant;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.utilities.OpeningHoursMapper;
import com.example.Restaurant.utilities.RestaurantMapper;
import jakarta.transaction.Transactional;
import org.example.exceptions.NoSuchElementExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Autowired
    private OpeningHoursMapper openingHoursMapper;

    @Transactional
    @Override
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        return restaurantMapper.toDTO(restaurantRepository.save(restaurant));
    }

    @Override
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.toDTO(restaurants);
    }

    @Override
    public RestaurantDTO getRestaurantById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found with id: " + id));
        return restaurantMapper.toDTO(restaurant);
    }

    @Transactional
    @Override
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found with id: " + id));

        restaurant.setName(restaurantDTO.getName());
        restaurant.setAddress(restaurantDTO.getAddress());
        restaurant.setOpeningHours(openingHoursMapper.toEntity(restaurantDTO.getOpeningHours()));

        return restaurantMapper.toDTO(restaurantRepository.save(restaurant));
    }

    @Override
    public String deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new NoSuchElementExistsException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
        return "Restaurant deleted Successfully!";
    }
}
