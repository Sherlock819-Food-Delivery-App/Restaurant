package com.example.Restaurant.controller;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.service.MenuManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant-management/menu")
public class MenuManagementController {

    private static final Logger logger = LoggerFactory.getLogger(MenuManagementController.class);

    @Autowired
    private MenuManagementService menuService;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenus() {
        Long restaurantId = getRestaurantIdFromAuth();
        logger.info("Fetching all menus for restaurant: {}", restaurantId);
        return ResponseEntity.ok(menuService.getAllMenus(restaurantId));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<List<MenuDTO>> getAllMenus(@PathVariable Long restaurantId) {
        logger.info("Fetching all menus for restaurant: {}", restaurantId);
        return ResponseEntity.ok(menuService.getAllMenus(restaurantId));
    }

    @PostMapping
    public ResponseEntity<MenuDTO> createMenu(@RequestBody MenuDTO menuDTO) {
        Long restaurantId = getRestaurantIdFromAuth();
        logger.info("Creating menu for restaurant: {}", restaurantId);
        return ResponseEntity.ok(menuService.createMenu(menuDTO, restaurantId));
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<MenuDTO> updateMenu(@PathVariable Long menuId, @RequestBody MenuDTO menuDTO) {
        logger.info("Updating menu with ID: {}", menuId);
        return ResponseEntity.ok(menuService.updateMenu(menuId, menuDTO));
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable Long menuId) {
        logger.info("Deleting menu with ID: {}", menuId);
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok("Menu deleted successfully!");
    }

    private Long getRestaurantIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // fetch username
        return restaurantRepo.findByOwnerId(restaurantOwnerRepo.findByEmail(username).getId()).orElseThrow().getId(); // fetch user based on JWT token details
    }
}

