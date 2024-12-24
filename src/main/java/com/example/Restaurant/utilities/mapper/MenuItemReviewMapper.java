package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.MenuItemReviewDTO;
import com.example.Restaurant.model.MenuItemReview;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MenuItemReviewMapper {

    MenuItemReviewDTO toDTO(MenuItemReview menuItemReview);

    MenuItemReview toEntity(MenuItemReviewDTO menuItemReviewDTO);

    List<MenuItemReviewDTO> toDTO(List<MenuItemReview> menuItemReviews);

    List<MenuItemReview> toEntity(List<MenuItemReviewDTO> menuItemReviewDTOs);
}

