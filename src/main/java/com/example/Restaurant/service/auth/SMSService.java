package com.example.Restaurant.service.auth;

public interface SMSService {
    void sendOtp(String toMobile, String otp);
}
