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
@Table(name = "menus")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private String name; // E.g., "Lunch Menu", "Dinner Menu"
    private String description;

    @ManyToOne
    @JoinColumn(name = "restaurantId", nullable = false)
    private Restaurant restaurant;

    // Menu can have many categories
    @OneToMany(mappedBy = "menu", cascade = CascadeType.PERSIST)
    private List<Category> categories;

    // Getters and Setters
}

