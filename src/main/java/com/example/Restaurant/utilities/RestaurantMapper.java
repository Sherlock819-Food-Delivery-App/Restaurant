package com.example.Restaurant.utilities;

import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, OpeningHoursMapper.class, MenuItemMapper.class})
public interface RestaurantMapper {

    // Convert entity to DTO
    RestaurantDTO toDTO(Restaurant restaurant);

    // Convert DTO to entity
    Restaurant toEntity(RestaurantDTO restaurantDTO);

    // List of Restaurant -> List of RestaurantDTO
    List<RestaurantDTO> toDTO(List<Restaurant> restaurants);

    // List of RestaurantDTO -> List of Restaurant
    List<Restaurant> toEntity(List<RestaurantDTO> restaurantDTOs);
}

