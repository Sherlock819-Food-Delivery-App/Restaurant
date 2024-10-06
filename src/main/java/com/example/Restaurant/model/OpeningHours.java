package com.example.Restaurant.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpeningHours {
    private LocalTime openTime;
    private LocalTime closeTime;

    public boolean isOpenNow() {
        LocalTime currentTime = LocalTime.now();
        if (closeTime.isBefore(openTime)) {
            // Restaurant closes after midnight
            return currentTime.isAfter(openTime) || currentTime.isBefore(closeTime);
        } else {
            // Restaurant closes the same day
            return currentTime.isAfter(openTime) && currentTime.isBefore(closeTime);
        }
    }

}

