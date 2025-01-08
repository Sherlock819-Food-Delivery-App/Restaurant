package com.example.Restaurant.dto;

import com.example.Restaurant.constant.RestaurantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String address;
    private String city;
    private String mobile;
    private String email;
    private Double rating;
    private Double latitude;
    private Double longitude;
    private RestaurantStatus status;
    private MenuDTO menu;
    private List<RestaurantReviewDTO> reviews;
    private List<WorkingHourDTO> workingHours;

    // Getters and Setters
}

