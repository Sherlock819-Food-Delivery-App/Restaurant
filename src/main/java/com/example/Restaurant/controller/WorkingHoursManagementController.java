package com.example.Restaurant.controller;

import com.example.Restaurant.dto.WorkingHourDTO;
import com.example.Restaurant.model.RestaurantOwnerPrincipal;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.service.WorkingHoursManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurant-management/working-hours")
public class WorkingHoursManagementController {

    private static final Logger logger = LoggerFactory.getLogger(WorkingHoursManagementController.class);

    @Autowired
    private WorkingHoursManagementService workingHoursService;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @GetMapping
    public ResponseEntity<List<WorkingHourDTO>> getAllWorkingHours() {
        Long restaurantId = getRestaurantIdFromAuth();
        logger.info("Fetching working hours for restaurant: {}", restaurantId);
        return ResponseEntity.ok(workingHoursService.getAllWorkingHours(restaurantId));
    }

    @PostMapping
    public ResponseEntity<WorkingHourDTO> addWorkingHours(@RequestBody WorkingHourDTO workingHourDTO) {
        Long restaurantId = getRestaurantIdFromAuth();
        logger.info("Adding working hours for restaurant: {}", restaurantId);
        return ResponseEntity.ok(workingHoursService.addWorkingHours(workingHourDTO, restaurantId));
    }

    @PutMapping("/{hourId}")
    public ResponseEntity<WorkingHourDTO> updateWorkingHours(@PathVariable Long hourId, @RequestBody WorkingHourDTO workingHourDTO) {
        logger.info("Updating working hours with ID: {}", hourId);
        return ResponseEntity.ok(workingHoursService.updateWorkingHours(hourId, workingHourDTO));
    }

    @DeleteMapping("/{hourId}")
    public ResponseEntity<String> deleteWorkingHours(@PathVariable Long hourId) {
        logger.info("Deleting working hours with ID: {}", hourId);
        workingHoursService.deleteWorkingHours(hourId);
        return ResponseEntity.ok("Working hours deleted successfully!");
    }

    private Long getRestaurantIdFromAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // fetch username
        return restaurantRepo.findByOwnerId(restaurantOwnerRepo.findByEmail(username).getId()).orElseThrow().getId(); // fetch user based on JWT token details
    }
}

