package com.example.Restaurant.repo;

import com.example.Restaurant.model.RestaurantOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantOwnerRepo extends JpaRepository<RestaurantOwner, Long> {

    RestaurantOwner findByEmail(String email);

    RestaurantOwner findByMobile(String mobile);
}
