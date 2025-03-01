package com.example.Restaurant.dataloader;

import com.example.Restaurant.constant.RestaurantStatus;
import com.example.Restaurant.model.*;
import com.example.Restaurant.repo.*;
import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import org.example.exceptions.NoSuchElementExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
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

    @Autowired
    private RestaurantReviewRepo restaurantReviewRepository;

    @Autowired
    private MenuItemReviewRepo menuItemReviewRepository;

    @Autowired
    private DeliveryAreaRepo deliveryAreaRepository;

    @Autowired
    private PromotionRepo promotionRepository;

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    private final Faker faker = new Faker();
    private final Random random = new Random();

    @PostConstruct
    public void loadData() {
        if (restaurantRepository.count() == 0) {
            int numberOfOwners = random.nextInt(4) + 1;
            loadRestaurantOwners(numberOfOwners);
        }
    }

    private void loadRestaurantOwners(int numberOfOwners) {
        List<RestaurantOwner> restaurantOwners = new ArrayList<>();

        for (int i = 0; i < numberOfOwners; i++) {
            RestaurantOwner restaurantOwner = RestaurantOwner.builder()
                    .email(faker.internet().emailAddress())
                    .mobile(faker.phoneNumber().phoneNumber())
                    .role("OWNER")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .version(1)
                    .build();

            restaurantOwners.add(restaurantOwner);
        }
        restaurantOwnerRepo.saveAll(restaurantOwners);

        restaurantOwners.forEach(this::loadRestaurants);
    }

    private void loadRestaurants(RestaurantOwner restaurantOwner) {
        List<Restaurant> restaurants = new ArrayList<>();

        Restaurant restaurant = Restaurant.builder()
                .name(faker.company().name())
                .description(faker.lorem().paragraph())
                .address(faker.address().fullAddress())
                .city(faker.address().city())
                .mobile(faker.phoneNumber().phoneNumber())
                .email(faker.internet().emailAddress())
                .rating(random.nextDouble() * 5) // Random rating between 0 and 5
                .latitude(Double.parseDouble(faker.address().latitude())) // Random latitude
                .longitude(Double.parseDouble(faker.address().longitude())) // Random longitude
                .status(randomRestaurantStatus())
                .owner(restaurantOwner)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .version(1)
                .build();

        restaurants.add(restaurant);

        restaurantRepository.saveAll(restaurants);

        restaurants.forEach(this::loadMenu);
        restaurants.forEach(this::loadWorkingHours);
        restaurants.forEach(this::loadDeliveryAreas);
        restaurants.forEach(this::loadReviews);
        restaurants.forEach(this::loadPromotions);
    }

    private void loadMenus(Restaurant restaurant) {
        int numberOfMenus = random.nextInt(2) + 1; // 1 to 3 menus per restaurant
        List<Menu> menus = new ArrayList<>();

        for (int i = 0; i < numberOfMenus; i++) {
            Menu menu = Menu.builder()
                    .description(faker.lorem().sentence())
                    .restaurant(restaurant)
                    .build();

            menus.add(menu);
        }

        menuRepository.saveAll(menus);
        menus.forEach(menu -> loadCategories(menu));
    }

    private void loadMenu(Restaurant restaurant) {
        Menu menu = Menu.builder()
                .description(faker.lorem().sentence())
                .restaurant(restaurant)
                .build();

        menuRepository.save(menu);
        loadCategories(menu);
    }

    private void loadCategories(Menu menu) {
        int numberOfCategories = random.nextInt(2) + 1; // 1 to 5 categories per menu
        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Category category = Category.builder()
                    .name(faker.food().ingredient() + " Category")
                    .description(faker.lorem().sentence())
                    .menu(menu)
                    .build();

            categories.add(category);
        }

        categoryRepository.saveAll(categories);
        categories.forEach(category -> loadMenuItems(category));
    }

    private void loadMenuItems(Category category) {
        int numberOfMenuItems = random.nextInt(2) + 1; // 1 to 10 menu items per category
        List<MenuItem> menuItems = new ArrayList<>();

        for (int i = 0; i < numberOfMenuItems; i++) {
            MenuItem menuItem = MenuItem.builder()
                    .name(faker.food().dish())
                    .description(faker.lorem().sentence())
                    .price(randomPrice(5.0, 50.0)) // Random price between $5 and $50
                    .available(true)
                    .category(category)
                    .rating(random.nextInt(6)) // Random rating between 0 and 5
                    .build();

            menuItems.add(menuItem);
        }

        menuItemRepository.saveAll(menuItems);
        menuItems.forEach(menuItem -> loadMenuItemReviews(menuItem));
    }

    private void loadMenuItemReviews(MenuItem menuItem) {
        int numberOfReviews = random.nextInt(2) + 1; // 1 to 5 reviews per menu item
        List<MenuItemReview> reviews = new ArrayList<>();

        for (int i = 0; i < numberOfReviews; i++) {
            MenuItemReview review = MenuItemReview.builder()
                    .menuItem(menuItem)
                    .userId(faker.number().randomNumber())
                    .rating(random.nextInt(6)) // Random rating between 0 and 5
                    .comment(faker.lorem().sentence())
                    .createdAt(LocalDateTime.now())
                    .build();

            reviews.add(review);
        }

        menuItemReviewRepository.saveAll(reviews);
    }

    private void loadWorkingHours(Restaurant restaurant) {
        List<WorkingHour> workingHours = new ArrayList<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            WorkingHour workingHour = WorkingHour.builder()
                    .dayOfWeek(day)
                    .openingTime(randomLocalTime(8, 11)) // Open between 8AM and 11AM
                    .closingTime(randomLocalTime(20, 23)) // Close between 8PM and 11PM
                    .restaurant(restaurant)
                    .build();

            workingHours.add(workingHour);
        }

        restaurant.setWorkingHours(workingHours); // Assuming you have a setter for workingHours
    }

    private void loadDeliveryAreas(Restaurant restaurant) {
        int numberOfAreas = random.nextInt(3) + 1; // 1 to 3 delivery areas per restaurant
        List<DeliveryArea> areas = new ArrayList<>();

        for (int i = 0; i < numberOfAreas; i++) {
            DeliveryArea area = DeliveryArea.builder()
                    .areaName(faker.address().city())
                    .restaurant(restaurant)
                    .build();

            areas.add(area);
        }

        deliveryAreaRepository.saveAll(areas);
    }

    private void loadReviews(Restaurant restaurant) {
        int numberOfReviews = random.nextInt(3) + 1; // 1 to 3 reviews per restaurant
        List<RestaurantReview> reviews = new ArrayList<>();

        for (int i = 0; i < numberOfReviews; i++) {
            RestaurantReview review = RestaurantReview.builder()
                    .restaurant(restaurant)
                    .userId((long) faker.number().numberBetween(1, 100))
                    .rating(random.nextInt(6)) // Random rating between 0 and 5
                    .comment(faker.lorem().sentence())
                    .createdAt(LocalDateTime.now())
                    .build();

            reviews.add(review);
        }

        restaurantReviewRepository.saveAll(reviews);
    }

    private void loadPromotions(Restaurant restaurant) {
        int numberOfPromotions = random.nextInt(2) + 1; // 1 to 2 promotions per restaurant
        List<Promotion> promotions = new ArrayList<>();

        for (int i = 0; i < numberOfPromotions; i++) {
            Promotion promotion = Promotion.builder()
                    .title(faker.commerce().productName() + " Discount")
                    .description(faker.lorem().sentence())
                    .discountPercentage(random.nextDouble(10, 51)) // Discount between 10% and 50%
                    .endDate(LocalDateTime.now().plusDays(random.nextInt(7, 30))) // Valid for 7 to 30 days
                    .restaurant(restaurant)
                    .build();

            promotions.add(promotion);
        }

        promotionRepository.saveAll(promotions);
    }

    private LocalTime randomLocalTime(int startHour, int endHour) {
        int hour = startHour + random.nextInt(endHour - startHour + 1);
        int minute = random.nextInt(60);
        return LocalTime.of(hour, minute);
    }

    private double randomPrice(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    private RestaurantStatus randomRestaurantStatus() {
        return random.nextBoolean() ? RestaurantStatus.OPEN : random.nextBoolean() ? RestaurantStatus.CLOSED : RestaurantStatus.BUSY;
    }
}
