package com.example.Restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String name;
    private String address;
    private OpeningHoursDTO openingHours;
    private List<MenuDTO> menus;
    private List<CategoryDTO> categories;
    private List<MenuItemDTO> menuItems;
}
