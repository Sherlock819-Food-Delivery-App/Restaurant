package com.example.Restaurant.service;

import com.example.Restaurant.dto.MenuItemDTO;
import com.example.Restaurant.model.MenuItem;
import com.example.Restaurant.repo.MenuItemRepo;
import com.example.Restaurant.utilities.MenuItemMapper;
import jakarta.transaction.Transactional;
import org.example.exceptions.NoSuchElementExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    private MenuItemRepo menuItemRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Transactional
    @Override
    public MenuItemDTO createMenuItem(MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
        return menuItemMapper.toDTO(menuItemRepository.save(menuItem));
    }

    @Override
    public List<MenuItemDTO> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemRepository.findAll();
        return menuItemMapper.toDTO(menuItems);
    }

    @Override
    public MenuItemDTO getMenuItemById(Long id) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu Item not found with id: " + id));
        return menuItemMapper.toDTO(menuItem);
    }

    @Transactional
    @Override
    public MenuItemDTO updateMenuItem(Long id, MenuItemDTO menuItemDTO) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu Item not found with id: " + id));
        return menuItemMapper.toDTO(menuItemRepository.save(existingMenuItem));
    }

    @Override
    public String deleteMenuItem(Long id) {
        MenuItem existingMenuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu Item not found with id: " + id));
        menuItemRepository.delete(existingMenuItem);
        return "Menu Item deleted successfullly!";
    }
}
