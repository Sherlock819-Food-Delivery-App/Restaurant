package com.example.Restaurant.model;

import com.example.Restaurant.constant.RestaurantStatus;
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
@Table(name = "restaurants")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String address;
    private String city;
    private String mobile;
    private String email;
    private Double rating;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId", nullable = false)
    @JsonBackReference
    private RestaurantOwner owner;

    @OneToOne(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion during serialization
    private Menu menu;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion during serialization
    private List<RestaurantReview> reviews;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion during serialization
    private List<WorkingHour> workingHours;

    // Geolocation fields
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private RestaurantStatus status; // OPEN, CLOSED, BUSY

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private int version;
}