package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.model.Menu;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface MenuMapper {

    MenuDTO toDTO(Menu menu);

    Menu toEntity(MenuDTO menuDTO);

    List<MenuDTO> toDTO(List<Menu> menus);

    List<Menu> toEntity(List<MenuDTO> menuDTOs);

    @AfterMapping
    default void setCategoriesInChildren(@MappingTarget Menu menu) {
        if (menu.getCategories() != null) {
            menu.getCategories().forEach(category -> category.setMenu(menu));
        }
    }

    @AfterMapping
    default void setMenuIdInChildDTOs(@MappingTarget MenuDTO menuDTO, Menu menu) {
        if (menuDTO.getCategories() != null) {
            menuDTO.getCategories().forEach(categoryDTO -> categoryDTO.setMenuId(menu.getId()));
        }
    }
}
