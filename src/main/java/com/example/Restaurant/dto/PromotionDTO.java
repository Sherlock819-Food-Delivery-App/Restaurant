package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private Long id;
    private String title;
    private String description;
    private Double discountPercentage;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long restaurantId; // Reference to the Restaurant

    // Getters and Setters
}

