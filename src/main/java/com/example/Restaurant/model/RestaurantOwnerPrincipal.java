package com.example.Restaurant.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class RestaurantOwnerPrincipal implements UserDetails {

    private RestaurantOwner restaurantOwner;

    public RestaurantOwnerPrincipal(RestaurantOwner restaurantOwner)
    {
        this.restaurantOwner = restaurantOwner;
    }

    public Long getRestaurantId() {
        return restaurantOwner.getId(); // assuming `getId()` retrieves the restaurant ID
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_RESTAURANT_OWNER"));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return restaurantOwner.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
