package com.example.Restaurant.utilities;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.model.Menu;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MenuMapper {

    // Convert entity to DTO
    MenuDTO toDTO(Menu menu);

    // Convert DTO to entity
    Menu toEntity(MenuDTO menuDTO);

    // List of MenuItem -> List of MenuItemDTO
    List<MenuDTO> toDTO(List<Menu> menus);

    // List of MenuItemDTO -> List of MenuItem
    List<Menu> toEntity(List<MenuDTO> menuDTOS);
}
