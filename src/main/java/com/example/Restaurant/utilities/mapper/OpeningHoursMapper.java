package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.OpeningHoursDTO;
import com.example.Restaurant.model.OpeningHours;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpeningHoursMapper {

    // Convert entity to DTO
    OpeningHoursDTO toDTO(OpeningHours openingHours);

    // Convert DTO to entity
    OpeningHours toEntity(OpeningHoursDTO openingHoursDTO);
}

