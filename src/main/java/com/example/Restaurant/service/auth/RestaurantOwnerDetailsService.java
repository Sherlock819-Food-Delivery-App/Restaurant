package com.example.Restaurant.service.auth;


import com.example.Restaurant.model.RestaurantOwner;
import com.example.Restaurant.model.RestaurantOwnerPrincipal;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RestaurantOwnerDetailsService implements UserDetailsService {

    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Fetch the user by email from the repository
        RestaurantOwner restaurantOwner = restaurantOwnerRepo.findByEmail(email);

        if (restaurantOwner == null)
            throw new UsernameNotFoundException("Owner not found with email: " + email);

        // Return an instance of CustomUserDetails
        return new RestaurantOwnerPrincipal(restaurantOwner);
    }
}
