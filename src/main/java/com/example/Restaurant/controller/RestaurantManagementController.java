package com.example.Restaurant.controller;

import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.service.RestaurantManagementService;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurant-management/restaurant")
public class RestaurantManagementController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagementController.class);

    @Autowired
    private RestaurantManagementService restaurantService;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @GetMapping
    public ResponseEntity<RestaurantDTO> getRestaurant() {
        Long ownerId = getOwnerIdFromAuth();
        logger.info("Fetching restaurant details for owner: {}", ownerId);
        try {
            if(ownerId == null)
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            return ResponseEntity.ok(restaurantService.getRestaurant(ownerId));
        } catch (NoSuchElementExistsException e) {
            logger.error("No restaurants found for owner : {}", e.getMessage());
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurant() {
        logger.info("Fetching all restaurants!!");
        try {
            return ResponseEntity.ok(restaurantService.getAllRestaurant());
        } catch (Exception e) {
            logger.error("No restaurants found!!");
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        Long ownerId = getOwnerIdFromAuth();
        logger.info("Creating restaurant for owner: {}", ownerId);
        try {
            return ResponseEntity.ok(restaurantService.createRestaurant(restaurantDTO, ownerId));
        } catch (Exception e) {
            logger.error("Error creating restaurant: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PutMapping
    public ResponseEntity<RestaurantDTO> updateRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        Long ownerId = getOwnerIdFromAuth();
        logger.info("Updating restaurant details for owner: {}", ownerId);
        try {
            return ResponseEntity.ok(restaurantService.updateRestaurant(restaurantDTO, ownerId));
        } catch (Exception e) {
            logger.error("Error updating restaurant: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRestaurant() {
        Long ownerId = getOwnerIdFromAuth();
        logger.info("Deleting restaurant for owner: {}", ownerId);
        try {
            restaurantService.deleteRestaurant(ownerId);
            return ResponseEntity.ok("Restaurant deleted successfully!");
        } catch (NoSuchElementExistsException e) {
            logger.error("Error deleting restaurant: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    private Long getOwnerIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // fetch username
        return restaurantOwnerRepo.findByEmail(username).getId(); // fetch user based on JWT token details
    }
}

