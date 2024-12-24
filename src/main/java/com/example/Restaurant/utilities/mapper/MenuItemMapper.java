package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.MenuItemDTO;
import com.example.Restaurant.model.MenuItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuItemReviewMapper.class})
public interface MenuItemMapper {

    MenuItemDTO toDTO(MenuItem menuItem);

    MenuItem toEntity(MenuItemDTO menuItemDTO);

    List<MenuItemDTO> toDTO(List<MenuItem> menuItems);

    List<MenuItem> toEntity(List<MenuItemDTO> menuItemDTOs);

    @AfterMapping
    default void setReviewsInChildren(@MappingTarget MenuItem menuItem) {
        if (menuItem.getReviews() != null) {
            menuItem.getReviews().forEach(review -> review.setMenuItem(menuItem));
        }
    }

    @AfterMapping
    default void setMenuItemIdInChildDTOs(@MappingTarget MenuItemDTO menuItemDTO, MenuItem menuItem) {
        if (menuItemDTO.getReviews() != null) {
            menuItemDTO.getReviews().forEach(reviewDTO -> reviewDTO.setMenuItemId(menuItem.getId()));
        }
    }
}
