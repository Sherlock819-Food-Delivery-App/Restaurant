package com.example.Restaurant.utilities.mapper;


import com.example.Restaurant.dto.RestaurantOwnerDTO;
import com.example.Restaurant.model.RestaurantOwner;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RestaurantOwner.class})
public interface RestaurantOwnerMapper {
    RestaurantOwnerDTO toDTO(RestaurantOwner restaurant);

    RestaurantOwner toEntity(RestaurantOwnerDTO restaurantDTO);

    List<RestaurantOwnerDTO> toDTO(List<RestaurantOwner> restaurants);

    List<RestaurantOwner> toEntity(List<RestaurantOwnerDTO> restaurantDTOs);

}
