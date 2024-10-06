package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Duration;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Duration preparationTime;  // Mapped to Duration
    private String categoryName;  // Optional to show the category name
    private Long restaurantId; // To associate with the Restaurant entity
}

