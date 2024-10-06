package com.example.Restaurant.utilities;

import com.example.Restaurant.dto.MenuItemDTO;
import com.example.Restaurant.model.MenuItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemMapper {

    // Convert entity to DTO
    MenuItemDTO toDTO(MenuItem menuItem);

    // Convert DTO to entity
    MenuItem toEntity(MenuItemDTO menuItemDTO);

    // List of MenuItem -> List of MenuItemDTO
    List<MenuItemDTO> toDTO(List<MenuItem> menuItems);

    // List of MenuItemDTO -> List of MenuItem
    List<MenuItem> toEntity(List<MenuItemDTO> menuItemDTOs);
}

