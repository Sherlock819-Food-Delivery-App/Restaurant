package com.example.Restaurant.repo;

import com.example.Restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem, Long> {

}
