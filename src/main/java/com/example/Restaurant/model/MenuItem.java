package com.example.Restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor // Generates constructor with parameters
@NoArgsConstructor // Generates no-argument constructor
@Entity
@Table(name = "menuItems")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId", nullable = false)
    @JsonBackReference // Prevents recursion during serialization
    private Category category;

    @OneToMany(mappedBy = "menuItem", cascade = CascadeType.ALL)
    @JsonManagedReference // Prevents recursion during serialization
    private List<MenuItemReview> reviews;

    private Integer rating; // Average rating based on reviews

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private int version;
    // Getters, Setters, Constructors
}

