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
    private String name;
    private String description;
    private Long restaurantId; // Optional: Reference to the parent Restaurant
    private List<CategoryDTO> categories; // To include categories associated with the menu

    // Getters and Setters
}

