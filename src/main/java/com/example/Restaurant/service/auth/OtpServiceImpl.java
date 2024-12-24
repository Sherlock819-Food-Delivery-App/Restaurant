package com.example.Restaurant.service.auth;


import com.example.Restaurant.config.auth.JwtTokenProvider;
import com.example.Restaurant.model.Otp;
import com.example.Restaurant.model.RestaurantOwner;
import com.example.Restaurant.repo.OtpRepository;
import com.example.Restaurant.repo.RestaurantOwnerRepo;
import com.example.Restaurant.utilities.ValidationUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;
    @Autowired
    private EmailServiceImpl emailServiceImpl; // Can use TwilioService if sending OTPs via SMS
    @Autowired
    private SMSService smsService; // Can use TwilioService if sending OTPs via SMS
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    ValidationUtilities validationUtilities;

    @Override
    public void generateAndSendOtp(String identifier, String role, Boolean isEmail) {
        RestaurantOwner existingRestaurantOwner;
        Otp existingOtp;

        // Validate identifier based on its type (email or mobile)
        if (isEmail) {
            if (!validationUtilities.isValidEmailAddress(identifier)) {
                throw new IllegalArgumentException("Invalid email: " + identifier);
            }
            existingRestaurantOwner = restaurantOwnerRepo.findByEmail(identifier);
            existingOtp = otpRepository.findByEmail(identifier);
        } else {
            if (!validationUtilities.isValidMobileNumber(identifier)) {
                throw new IllegalArgumentException("Invalid mobile: " + identifier);
            }
            existingRestaurantOwner = restaurantOwnerRepo.findByMobile(identifier);
            existingOtp = otpRepository.findByMobile(identifier);
        }

        // Generate OTP (6-digit random number)
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Set OTP expiration time (e.g., 5 minutes)
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5);

        // Save emailId or mobile number to user DB
        if (existingRestaurantOwner == null) {
            RestaurantOwner restaurantOwner = new RestaurantOwner();
            if (isEmail) {
                restaurantOwner.setEmail(identifier);
            } else {
                restaurantOwner.setMobile(identifier);
            }
            restaurantOwner.setRole(role);
            restaurantOwnerRepo.save(restaurantOwner);
        }

        // Save OTP to the database
        Otp otpEntity = existingOtp != null ? existingOtp : new Otp();
        otpEntity.setOtp(otp);
        if (isEmail) {
            otpEntity.setEmail(identifier);
        } else {
            otpEntity.setMobile(identifier);
        }
        otpEntity.setExpirationTime(expirationTime);
        otpRepository.save(otpEntity);

        // Send OTP via email or SMS
        if (isEmail) {
            emailServiceImpl.sendOtp(identifier, otp);
        } else {
            smsService.sendOtp(identifier, otp);
        }
    }

    @Override
    public String validateOtpForEmail(String email, String otp) {
        // Find OTP in the database
        Otp otpEntity = otpRepository.findByEmailAndOtp(email, otp);

        // Check if OTP exists and is still valid
        if (otpEntity == null || isOtpExpired(otpEntity)) {
            throw new IllegalArgumentException("Invalid or expired OTP.");
        }

        RestaurantOwner restaurantOwner = restaurantOwnerRepo.findByEmail(email);

        // OTP is valid, generate JWT token for the user
        return jwtTokenProvider.generateToken(email, restaurantOwner.getRole());
    }

    private boolean isOtpExpired(Otp otpEntity) {
        return otpEntity.getExpirationTime().isBefore(LocalDateTime.now());
    }

}
