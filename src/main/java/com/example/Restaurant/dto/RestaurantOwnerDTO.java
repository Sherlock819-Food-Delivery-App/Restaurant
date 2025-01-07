package com.example.Restaurant.dto;

import com.example.Restaurant.model.Restaurant;
import lombok.Data;

@Data
public class RestaurantOwnerDTO {
    private Long id;
    private String email; // Can be used for sending OTP or notifications
    private String mobile; // Also used for OTP
    private String role;
    private Restaurant restaurant;
}
