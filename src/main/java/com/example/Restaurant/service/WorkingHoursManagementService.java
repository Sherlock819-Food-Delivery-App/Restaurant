package com.example.Restaurant.service;

import com.example.Restaurant.dto.WorkingHourDTO;
import org.example.exceptions.NoSuchElementExistsException;

import java.util.List;

public interface WorkingHoursManagementService {
    List<WorkingHourDTO> getAllWorkingHours(Long restaurantId);
    WorkingHourDTO addWorkingHours(WorkingHourDTO workingHourDTO, Long restaurantId);
    WorkingHourDTO updateWorkingHours(Long hourId, WorkingHourDTO workingHourDTO) throws NoSuchElementExistsException;
    void deleteWorkingHours(Long hourId) throws NoSuchElementExistsException;
}

