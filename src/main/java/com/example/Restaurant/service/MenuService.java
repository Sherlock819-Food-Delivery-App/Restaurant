package com.example.Restaurant.service;

import com.example.Restaurant.dto.MenuDTO;

import java.util.List;

public interface MenuService {
    MenuDTO createMenu(MenuDTO menuDTO);
    MenuDTO getMenuById(Long id);
    List<MenuDTO> getAllMenus();
    MenuDTO updateMenu(Long id, MenuDTO menuDTO);
    String deleteMenu(Long id);
}
