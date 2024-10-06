package com.example.Restaurant.dataloader;

import com.example.Restaurant.model.*;
import com.example.Restaurant.repo.CategoryRepository;
import com.example.Restaurant.repo.MenuItemRepo;
import com.example.Restaurant.repo.MenuRepository;
import com.example.Restaurant.repo.RestaurantRepo;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataLoader {

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MenuItemRepo menuItemRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @PostConstruct
    public void loadData() {
        if (restaurantRepository.count() == 0) {
            int numberOfRestaurants = 5; // Create 5 restaurants (you can change the number dynamically)
            loadRestaurants(numberOfRestaurants);
        }
    }

    // Method to create multiple restaurants
    private void loadRestaurants(int numberOfRestaurants) {
        List<Restaurant> restaurants = new ArrayList<>();

        for (int i = 0; i < numberOfRestaurants; i++) {
            Restaurant restaurant = Restaurant.builder()
                    .name(generateRestaurantName())
                    .address(faker.address().fullAddress())
                    .mobile(faker.phoneNumber().phoneNumber())
                    .email(faker.internet().emailAddress())
                    .isActive(true)
                    .openingHours(new OpeningHours(
                            randomLocalTime(8, 11), // Open between 8AM and 11AM
                            randomLocalTime(20, 23) // Close between 8PM and 11PM
                    ))
                    .build();

            restaurants.add(restaurant);
        }

        restaurantRepository.saveAll(restaurants);

        // For each restaurant, load dynamic number of menus, categories, and menu items
        restaurants.forEach(this::loadMenus);
    }

    // Custom method to generate random restaurant names
    private String generateRestaurantName() {
        String[] adjectives = {"Fancy", "Cozy", "Tasty", "Spicy", "Sweet", "Savory", "Elegant"};
        String[] nouns = {"Bistro", "Grill", "Cafe", "Diner", "Eatery", "Kitchen", "Table"};
        return faker.options().option(adjectives) + " " + faker.options().option(nouns);
    }

    // Method to create menus dynamically for each restaurant
    private void loadMenus(Restaurant restaurant) {
        int numberOfMenus = random.nextInt(3) + 1; // Randomly create 1 to 3 menus per restaurant
        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < numberOfMenus; i++) {
            Menu menu = Menu.builder()
                    .name(faker.food().dish() + " Menu") // Meaningful menu names
                    .description(faker.lorem().sentence())
                    .restaurant(restaurant)
                    .build();

            menus.add(menu);
        }

        menuRepository.saveAll(menus);

        // For each menu, load dynamic number of categories
        menus.forEach(menu -> loadCategories(menu, restaurant));
    }

    // Method to create categories dynamically for each menu
    private void loadCategories(Menu menu, Restaurant restaurant) {
        int numberOfCategories = random.nextInt(5) + 1; // Randomly create 1 to 5 categories per menu
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Category category = Category.builder()
                    .name(faker.food().ingredient() + " Category")
                    .menu(menu)
                    .restaurant(restaurant)
                    .build();

            categories.add(category);
        }

        categoryRepository.saveAll(categories);

        // For each category, load dynamic number of menu items
        categories.forEach(category -> loadMenuItems(category, restaurant));
    }

    // Method to create menu items dynamically for each category
    private void loadMenuItems(Category category, Restaurant restaurant) {
        int numberOfMenuItems = random.nextInt(10) + 1; // Randomly create 1 to 10 menu items per category
        List<MenuItem> menuItems = new ArrayList<>();

        for (int i = 0; i < numberOfMenuItems; i++) {
            MenuItem menuItem = MenuItem.builder()
                    .name(faker.food().dish())
                    .description(faker.lorem().sentence())
                    .price(randomPrice(5.0, 50.0)) // Random price between $5 and $50
                    .preparationTime(Duration.ofMinutes(random.nextInt(30) + 10)) // Random prep time between 10 to 40 minutes
                    .category(category)
                    .restaurant(restaurant)
                    .build();

            menuItems.add(menuItem);
        }

        menuItemRepository.saveAll(menuItems);
    }

    // Helper method to generate random LocalTime between a start and end hour
    private LocalTime randomLocalTime(int startHour, int endHour) {
        int hour = startHour + random.nextInt(endHour - startHour + 1);
        int minute = random.nextInt(60);
        return LocalTime.of(hour, minute);
    }

    // Helper method to generate random price between a range
    private double randomPrice(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
}
