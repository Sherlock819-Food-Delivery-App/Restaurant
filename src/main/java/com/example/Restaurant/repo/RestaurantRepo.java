package com.example.Restaurant.repo;


import com.example.Restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {

    Restaurant findByRestaurantIdOrMobileOrEmail(Long restaurantId, String mobile, String email);

    Restaurant findByMobileOrEmail(String mobile, String email);
}
