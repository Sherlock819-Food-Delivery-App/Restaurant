package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.WorkingHourDTO;
import com.example.Restaurant.model.WorkingHour;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WorkingHourMapper {

    WorkingHourDTO toDTO(WorkingHour workingHour);

    WorkingHour toEntity(WorkingHourDTO workingHourDTO);

    List<WorkingHourDTO> toDTO(List<WorkingHour> workingHours);

    List<WorkingHour> toEntity(List<WorkingHourDTO> workingHourDTOs);
}

