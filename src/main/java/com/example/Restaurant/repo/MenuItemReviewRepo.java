package com.example.Restaurant.repo;

import com.example.Restaurant.model.MenuItemReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemReviewRepo extends JpaRepository<MenuItemReview, Long> {
}
