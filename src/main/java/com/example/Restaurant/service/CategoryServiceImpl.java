package com.example.Restaurant.service;

import com.example.Restaurant.dto.CategoryDTO;
import com.example.Restaurant.model.Category;
import com.example.Restaurant.repo.CategoryRepository;
import com.example.Restaurant.utilities.CategoryMapper;
import jakarta.transaction.Transactional;
import org.example.exceptions.NoSuchElementExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.toEntity(categoryDTO);
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toDTO(categories);
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Category not found with id: " + id));
        return categoryMapper.toDTO(category);
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Category not found with id: " + id));
        return categoryMapper.toDTO(categoryRepository.save(existingCategory));
    }

    @Override
    public String deleteCategory(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Category not found with id: " + id));
        categoryRepository.delete(existingCategory);
        return "Category Deleted Successfully!";
    }
}
