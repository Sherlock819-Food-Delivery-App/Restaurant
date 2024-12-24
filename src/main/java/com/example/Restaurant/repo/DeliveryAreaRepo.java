package com.example.Restaurant.repo;

import com.example.Restaurant.model.DeliveryArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAreaRepo extends JpaRepository<DeliveryArea, Long> {
}
