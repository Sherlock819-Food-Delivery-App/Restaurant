package com.example.Restaurant.service;

import com.example.Restaurant.dto.CategoryDTO;
import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.dto.MenuItemDTO;
import org.example.exceptions.NoSuchElementExistsException;

import java.util.List;

public interface MenuManagementService {
    List<MenuDTO> getMenu(Long menuId);

    List<MenuDTO> getAllMenus(Long restaurantId);

    List<CategoryDTO> getCategories(Long restaurantId);

    List<MenuItemDTO> getMenuItems(Long restaurantId);

    MenuDTO createMenu(MenuDTO menuDTO, Long restaurantId);

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(CategoryDTO categoryDTO);

    boolean deleteCategory(Long categoryId);

    MenuItemDTO addItem(Long categoryId, MenuItemDTO menuItemDTO);

    MenuItemDTO updateItem(Long categoryId, MenuItemDTO menuItemDTO);

    boolean deleteItem(Long ietemId);

    MenuDTO updateMenu(Long menuId, MenuDTO menuDTO) throws NoSuchElementExistsException;

    void deleteMenu(Long menuId) throws NoSuchElementExistsException;
}

