package com.example.Restaurant.dto;

import lombok.Data;

@Data
public class OrderUpdateEvent {
    private final OrderDTO order;
    private final String restaurantId;

    public OrderUpdateEvent(OrderDTO order, String restaurantId) {
        this.order = order;
        this.restaurantId = restaurantId;
    }
}
