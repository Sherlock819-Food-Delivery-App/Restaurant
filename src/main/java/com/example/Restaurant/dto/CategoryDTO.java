package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Long menuId; // Optional: Reference to the parent Menu
    private List<MenuItemDTO> menuItems; // To include menu items associated with the category

    // Getters and Setters
}

