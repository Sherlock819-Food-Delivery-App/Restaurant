package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long id;
    private String description;
    private Long restaurantId; // Reference to the Restaurant
    private List<CategoryDTO> categories;

    // Getters and Setters
}


