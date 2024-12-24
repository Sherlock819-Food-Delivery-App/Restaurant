package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemReviewDTO {
    private Long id;
    private Integer rating;
    private String comment;
    private Long menuItemId; // Reference to the MenuItem
    private Long userId; // User ID who submitted the review

    // Getters and Setters
}

