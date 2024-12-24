package com.example.Restaurant.config.auth;

import com.example.Restaurant.service.auth.RestaurantOwnerDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RestaurantOwnerDetailsService restaurantOwnerDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Check if the user is already authenticated
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            // User is already authenticated, proceed to the next filter in the chain
            filterChain.doFilter(request, response);
            return; // Exit the filter chain
        }

        String userAgent = request.getHeader("User-Agent");
        String path = request.getRequestURI();

        // Skip JWT verification for public endpoints or Feign requests
        if (path.startsWith("/restaurant-management/restaurant/all") || (userAgent != null && userAgent.contains("Feign"))) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getJwtFromRequest(request);
        if (token != null && jwtTokenProvider.validateToken(token)) {

            String email = jwtTokenProvider.getEmailFromToken(token);

            // Load the user details from the database
            UserDetails userDetails = restaurantOwnerDetailsService.loadUserByUsername(email);

            if (userDetails == null) {
                throw new UsernameNotFoundException("User not found for the provided email");
            }

            // Create an authentication token using the UserDetails
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

