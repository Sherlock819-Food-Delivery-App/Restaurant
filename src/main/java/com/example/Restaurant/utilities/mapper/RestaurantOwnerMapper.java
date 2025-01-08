package com.example.Restaurant.utilities.mapper;


import com.example.Restaurant.dto.RestaurantOwnerDTO;
import com.example.Restaurant.model.RestaurantOwner;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {RestaurantMapper.class})
public interface RestaurantOwnerMapper {
    RestaurantOwnerDTO toDTO(RestaurantOwner restaurantOwner);

    RestaurantOwner toEntity(RestaurantOwnerDTO restaurantOwnerDTO);

    List<RestaurantOwnerDTO> toDTO(List<RestaurantOwner> restaurantOwners);

    List<RestaurantOwner> toEntity(List<RestaurantOwnerDTO> restaurantOwnerDTOs);

    @AfterMapping
    default void setRestaurantOwnerInChild(@MappingTarget RestaurantOwner restaurantOwner) {
        if (restaurantOwner.getRestaurant() != null) {
            restaurantOwner.getRestaurant().setOwner(restaurantOwner);
        }
    }

    @AfterMapping
    default void setRestaurantOwnerIdInChildDTO(@MappingTarget RestaurantOwnerDTO restaurantOwnerDTO, RestaurantOwner restaurantOwner) {
        if (restaurantOwnerDTO.getRestaurant() != null) {
            restaurantOwnerDTO.getRestaurant().setOwnerId(restaurantOwner.getId());
        }
    }

}
