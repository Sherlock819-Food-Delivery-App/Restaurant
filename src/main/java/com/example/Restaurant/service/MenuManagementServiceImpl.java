package com.example.Restaurant.service;

import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.model.Menu;
import com.example.Restaurant.model.Restaurant;
import com.example.Restaurant.repo.MenuRepository;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.utilities.mapper.MenuMapper;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuManagementServiceImpl implements MenuManagementService {

    private static final Logger logger = LoggerFactory.getLogger(MenuManagementServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<MenuDTO> getMenu(Long restaurantId) {
        List<Menu> menus = menuRepository.findByRestaurantId(restaurantId);
        return menuMapper.toDTO(menus);
    }

    @Override
    public List<MenuDTO> getAllMenus(Long restaurantId) {
        List<Menu> menus = menuRepository.findByRestaurantId(restaurantId);
        return menuMapper.toDTO(menus);
    }

    @Override
    public MenuDTO createMenu(MenuDTO menuDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found with id: " + restaurantId));
        Menu menu = menuMapper.toEntity(menuDTO);
        menu.setRestaurant(restaurant);
        Menu savedMenu = menuRepository.save(menu);
        return menuMapper.toDTO(savedMenu);
    }

    @Override
    public MenuDTO updateMenu(Long menuId, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + menuId));
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        Menu updatedMenu = menuRepository.save(menu);
        return menuMapper.toDTO(updatedMenu);
    }

    @Override
    public void deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new NoSuchElementExistsException("Menu not found with id: " + menuId);
        }
        menuRepository.deleteById(menuId);
        logger.info("Deleted menu with id: {}", menuId);
    }
}

