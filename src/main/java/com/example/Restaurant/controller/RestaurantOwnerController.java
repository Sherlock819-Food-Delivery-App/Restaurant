package com.example.Restaurant.controller;

import com.example.Restaurant.dto.RestaurantOwnerDTO;
import com.example.Restaurant.service.RestaurantOwnerService;
import com.example.Restaurant.utilities.mapper.RestaurantOwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private RestaurantOwnerMapper restaurantOwnerMapper;

    @GetMapping("/isAuthenticated")
    public Boolean isAuthenticated() {
        return restaurantOwnerService.isAuthenticated();
    }

    @GetMapping("/getRestaurantOwnerProfile")
    public ResponseEntity<?> getRestaurantOwnerProfile() {
        RestaurantOwnerDTO userResponseDTO = restaurantOwnerMapper.toDTO(restaurantOwnerService.getRestaurantOwner());

        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
}
