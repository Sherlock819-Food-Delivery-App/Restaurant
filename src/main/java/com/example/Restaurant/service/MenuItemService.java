package com.example.Restaurant.service;


import com.example.Restaurant.dto.MenuItemDTO;

import java.util.List;

public interface MenuItemService {
    MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO);

    MenuItemDTO getMenuItemById(Long id);

    List<MenuItemDTO> getAllMenuItems();

    MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO);

    String deleteMenuItem(Long id);
}
