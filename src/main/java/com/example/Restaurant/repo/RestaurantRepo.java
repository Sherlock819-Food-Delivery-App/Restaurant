package com.example.Restaurant.repo;


import com.example.Restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant, Long> {


    Restaurant findByMobileOrEmail(String mobile, String email);

    Restaurant findByEmail(String email);

    Restaurant findByMobile(String mobile);

    Restaurant findByOwnerEmail(String email);

    Optional<Restaurant> findByOwnerId(Long ownerId);
}
