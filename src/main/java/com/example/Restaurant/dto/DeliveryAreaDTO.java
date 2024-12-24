package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAreaDTO {
    private Long id;
    private String postalCode;
    private String areaName;
    private Long restaurantId; // Reference to the Restaurant

    // Getters and Setters
}

