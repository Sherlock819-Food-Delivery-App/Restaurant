package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.CategoryDTO;
import com.example.Restaurant.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {MenuItemMapper.class})
public interface CategoryMapper {

    CategoryDTO toDTO(Category category);

    Category toEntity(CategoryDTO categoryDTO);

    List<CategoryDTO> toDTO(List<Category> categories);

    List<Category> toEntity(List<CategoryDTO> categoryDTOs);

    @AfterMapping
    default void setMenuInChildren(@MappingTarget Category category) {
        if (category.getItems() != null) {
            category.getItems().forEach(item -> item.setCategory(category));
        }
    }

    @AfterMapping
    default void setCategoryIdInChildDTOs(@MappingTarget CategoryDTO categoryDTO, Category category) {
        if (categoryDTO.getItems() != null) {
            categoryDTO.getItems().forEach(itemDTO -> itemDTO.setCategoryId(category.getId()));
        }
    }
}
