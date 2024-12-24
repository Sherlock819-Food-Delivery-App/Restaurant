package com.example.Restaurant.controller;



import com.example.Restaurant.dto.auth.LoginRequestDTO;
import com.example.Restaurant.dto.auth.LoginValidationDTO;
import com.example.Restaurant.service.auth.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private OtpService otpService;

    // Endpoint for requesting an OTP
    @PostMapping("/request-otp")
    public ResponseEntity<String> requestOtp(@RequestBody LoginRequestDTO otpRequest) {
        try {
            if (otpRequest.getEmail() != null) {
                otpService.generateAndSendOtp(otpRequest.getEmail(), otpRequest.getRole(), true);
                return ResponseEntity.ok("OTP sent to " + otpRequest.getEmail());
            } else if (otpRequest.getMobile() != null) {
                otpService.generateAndSendOtp(otpRequest.getMobile(), otpRequest.getRole(), false);
                return ResponseEntity.ok("OTP sent to " + otpRequest.getMobile());
            } else {
                return ResponseEntity.badRequest().body("Please provide either an email or mobile number.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred while sending OTP.");
        }
    }

    // Endpoint for validating the OTP
    @PostMapping("/validate-otp")
    public ResponseEntity<String> validateOtp(@RequestBody LoginValidationDTO otpValidationRequest) {
        try {
            String jwtToken = otpService.validateOtpForEmail(otpValidationRequest.getEmail(), otpValidationRequest.getOtp());
            return ResponseEntity.ok(jwtToken);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("An error occurred during OTP validation.");
        }
    }
}

