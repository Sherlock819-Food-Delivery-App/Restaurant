package com.example.Restaurant.controller;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.dto.RestaurantDTO;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.service.MenuManagementService;
import com.example.Restaurant.service.RestaurantService;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantManagementController.class);

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Autowired
    private MenuManagementService menuService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long restaurantId) {
        logger.info("Fetching details for restaurant : {}", restaurantId);
        try {
            return ResponseEntity.ok(restaurantService.getRestaurant(restaurantId));
        } catch (NoSuchElementExistsException e) {
            logger.error("Error fetching restaurant: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("{restaurantId}/menu")
    public ResponseEntity<List<MenuDTO>> getAllMenus(@PathVariable Long restaurantId) {
        logger.info("Fetching all menus for restaurant: {}", restaurantId);
        return ResponseEntity.ok(menuService.getAllMenus(restaurantId));
    }
}
