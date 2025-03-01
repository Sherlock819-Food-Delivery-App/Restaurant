package com.example.Restaurant.service;

import com.example.Restaurant.dto.CategoryDTO;
import com.example.Restaurant.dto.MenuDTO;
import com.example.Restaurant.dto.MenuItemDTO;
import com.example.Restaurant.model.Category;
import com.example.Restaurant.model.Menu;
import com.example.Restaurant.model.MenuItem;
import com.example.Restaurant.model.Restaurant;
import com.example.Restaurant.repo.CategoryRepository;
import com.example.Restaurant.repo.MenuItemRepo;
import com.example.Restaurant.repo.MenuRepository;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.utilities.mapper.CategoryMapper;
import com.example.Restaurant.utilities.mapper.MenuItemMapper;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuItemMapper menuItemMapper;

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @Autowired
    private MenuItemRepo menuItemRepository;

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
    public List<CategoryDTO> getCategories(Long restaurantId) {
        return categoryMapper.toDTO(menuRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(Menu::getCategories)
                .flatMap(List::stream)
                .toList());
    }

    @Override
    public List<MenuItemDTO> getMenuItems(Long restaurantId) {
        return getCategories(restaurantId).stream().map(CategoryDTO::getItems).flatMap(List::stream).toList();
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
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Restaurant restaurant = restaurantOwnerService.getRestaurantOwner().getRestaurant();
        if (restaurant.getMenu() != null) {  // Check if menuId is not null
            Menu menu = menuRepository.findById(restaurant.getMenu().getId())
                    .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + restaurant.getMenu().getId()));
            //Saving and returning category
            Category category = categoryMapper.toEntity(categoryDTO);
            category.setMenu(menu);
            return categoryMapper.toDTO(categoryRepository.save(category));
        }
        else {
            //Creating menu for restaurant
            Menu menu = Menu.builder().restaurant(restaurant).build();
            menuRepository.save( menu);

            //Assigning menu to restaurant
            restaurant.setMenu(menu);
            restaurantRepository.save(restaurant);

            //Saving and returning category
            Category category = categoryMapper.toEntity(categoryDTO);
            category.setMenu(menu);
            return categoryMapper.toDTO(categoryRepository.save(category));
        }
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new NoSuchElementExistsException("Category not found with id: " + categoryDTO.getId()));
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return categoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public boolean deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementExistsException("Category not found with id: " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
        logger.info("Deleted category with id: {}", categoryId);
        return true;
    }

    @Override
    public MenuItemDTO addItem(Long categoryId, MenuItemDTO menuItemDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoSuchElementExistsException("Category not found with id: " + categoryId));
        MenuItem menuItem = menuItemMapper.toEntity(menuItemDTO);
        menuItem.setCategory(category);
        MenuItem savedMenuItem = menuItemRepository.save(menuItem);
        return menuItemMapper.toDTO(savedMenuItem);
    }

    @Override
    public MenuItemDTO updateItem(Long categoryId, MenuItemDTO menuItemDTO) {
        MenuItem menuItem = menuItemRepository.findById(menuItemDTO.getId())
                .orElseThrow(() -> new NoSuchElementExistsException("Item not found with id: " + menuItemDTO.getId()));
        menuItem.setName(menuItemDTO.getName());
        menuItem.setDescription(menuItemDTO.getDescription());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setAvailable(menuItemDTO.getAvailable());
        return menuItemMapper.toDTO(menuItemRepository.save(menuItem));
    }

    @Override
    public boolean deleteItem(Long itemId) {
        if (!menuItemRepository.existsById(itemId)) {
            throw new NoSuchElementExistsException("Item not found with id: " + itemId);
        }
        menuItemRepository.deleteById(itemId);
        logger.info("Deleted item with id: {}", itemId);
        return true;
    }

    @Override
    public MenuDTO updateMenu(Long menuId, MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NoSuchElementExistsException("Menu not found with id: " + menuId));
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

