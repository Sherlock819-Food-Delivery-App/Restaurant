package com.example.Restaurant.utilities;

import com.example.Restaurant.dto.CategoryDTO;
import com.example.Restaurant.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = MenuItemMapper.class)
public interface CategoryMapper {

    // Convert entity to DTO
    CategoryDTO toDTO(Category category);

    // Convert DTO to entity
    Category toEntity(CategoryDTO categoryDTO);

    // List of Category -> List of CategoryDTO
    List<CategoryDTO> toDTO(List<Category> categories);

    // List of CategoryDTO -> List of Category
    List<Category> toEntity(List<CategoryDTO> categoryDTOs);
}

