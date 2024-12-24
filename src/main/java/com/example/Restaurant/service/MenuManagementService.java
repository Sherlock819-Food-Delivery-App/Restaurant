package com.example.Restaurant.service;

import com.example.Restaurant.dto.MenuDTO;
import org.example.exceptions.NoSuchElementExistsException;

import java.util.List;

public interface MenuManagementService {
    List<MenuDTO> getAllMenus(Long restaurantId);
    MenuDTO createMenu(MenuDTO menuDTO, Long restaurantId);
    MenuDTO updateMenu(Long menuId, MenuDTO menuDTO) throws NoSuchElementExistsException;
    void deleteMenu(Long menuId) throws NoSuchElementExistsException;
}

