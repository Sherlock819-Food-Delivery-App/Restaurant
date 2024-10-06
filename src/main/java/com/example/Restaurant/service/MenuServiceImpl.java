package com.example.Restaurant.service;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.model.Menu;
import com.example.Restaurant.repo.MenuRepository;
import com.example.Restaurant.utilities.MenuMapper;
import jakarta.transaction.Transactional;
import org.example.exceptions.NoSuchElementExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Transactional
    @Override
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Menu menu = menuMapper.toEntity(menuDTO);
        return menuMapper.toDTO(menuRepository.save(menu));
    }

    @Override
    public List<MenuDTO> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menuMapper.toDTO(menus);
    }

    @Override
    public MenuDTO getMenuById(Long id) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + id));
        return menuMapper.toDTO(menu);
    }

    @Transactional
    @Override
    public MenuDTO updateMenu(Long id, MenuDTO menuDTO) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + id));
        return menuMapper.toDTO(menuRepository.save(existingMenu));
    }

    @Override
    public String deleteMenu(Long id) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + id));
        menuRepository.delete(existingMenu);
        return "Menu Deleted Successfully!";
    }
}
