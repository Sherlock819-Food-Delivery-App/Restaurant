package com.example.Restaurant.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "restaurant_owners")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email; // Can be used for sending OTP or notifications
    private String mobile; // Also used for OTP
    private String role;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Restaurant restaurant;

    // Additional fields, constructors, getters, and setters
}

