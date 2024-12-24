package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean available;
    private Long categoryId; // Reference to the Category
    private Integer rating; // Average rating based on reviews
    private List<MenuItemReviewDTO> reviews;

    // Getters and Setters
}


