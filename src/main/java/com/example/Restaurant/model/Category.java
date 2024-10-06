package com.example.Restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name; // E.g., "Appetizers", "Main Course", "Desserts"

    @ManyToOne
    @JoinColumn(name = "menuId", nullable = false)
    private Menu menu;

    // Category can have many menu items
    @OneToMany(mappedBy = "category", cascade = CascadeType.PERSIST)
    private List<MenuItem> menuItems;

    // Added reference to the Restaurant for easy access
    @ManyToOne
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

}

