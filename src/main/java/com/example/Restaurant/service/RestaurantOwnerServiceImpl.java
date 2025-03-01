package com.example.Restaurant.service;

import com.example.Restaurant.dto.RestaurantOwnerDTO;
import com.example.Restaurant.model.RestaurantOwner;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.utilities.mapper.RestaurantOwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepository;

    @Autowired
    private RestaurantOwnerMapper restaurantOwnerMapper;

    @Override
    public RestaurantOwnerDTO createRestaurantOwner(RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = restaurantOwnerMapper.toEntity(restaurantOwnerDTO);
        restaurantOwner = restaurantOwnerRepository.save(restaurantOwner);
        return restaurantOwnerMapper.toDTO(restaurantOwner);
    }

    @Override
    public List<RestaurantOwnerDTO> getAllRestaurantOwners() {
        List<RestaurantOwner> restaurantOwners = restaurantOwnerRepository.findAll();
        return restaurantOwners.stream()
                .map(restaurantOwner -> restaurantOwnerMapper.toDTO(restaurantOwner))
                .collect(Collectors.toList());
    }

    @Override
    public RestaurantOwnerDTO getRestaurantOwnerById(Long id) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(id).orElseThrow();
        return restaurantOwnerMapper.toDTO(restaurantOwner);
    }

    @Override
    public RestaurantOwnerDTO updateRestaurantOwner(Long id, RestaurantOwnerDTO restaurantOwnerDTO) {
        RestaurantOwner restaurantOwner = restaurantOwnerRepository.findById(id).orElseThrow();
        restaurantOwnerMapper.toEntity(restaurantOwnerDTO);
        restaurantOwner = restaurantOwnerRepository.save(restaurantOwner);
        return restaurantOwnerMapper.toDTO(restaurantOwner);
    }

    @Override
    public void deleteRestaurantOwner(Long id) {
        restaurantOwnerRepository.deleteById(id);
    }

    @Override
    public boolean isAuthenticated() {
        return getPrincipalUser() != null;
    }

    @Override
    public RestaurantOwner getRestaurantOwner() {
        return getPrincipalUser();
    }

    private RestaurantOwner getPrincipalUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // fetch username
        return restaurantOwnerRepository.findByEmail(username); // fetch user based on JWT token details
    }
}
