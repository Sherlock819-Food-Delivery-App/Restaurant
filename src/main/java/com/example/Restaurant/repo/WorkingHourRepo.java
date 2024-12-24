package com.example.Restaurant.repo;

import com.example.Restaurant.model.WorkingHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkingHourRepo extends JpaRepository<WorkingHour, Long> {

    List<WorkingHour> findByRestaurantId(Long restaurantId);
}
