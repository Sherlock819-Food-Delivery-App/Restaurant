package com.example.Restaurant.service;

import com.example.Restaurant.dto.WorkingHourDTO;
import com.example.Restaurant.model.Restaurant;
import com.example.Restaurant.model.WorkingHour;
import com.example.Restaurant.repo.RestaurantRepo;
import com.example.Restaurant.repo.WorkingHourRepo;
import com.example.Restaurant.utilities.mapper.WorkingHourMapper;
import org.example.exceptions.NoSuchElementExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkingHoursManagementServiceImpl implements WorkingHoursManagementService {

    private static final Logger logger = LoggerFactory.getLogger(WorkingHoursManagementServiceImpl.class);

    @Autowired
    private WorkingHourRepo workingHourRepository;

    @Autowired
    private RestaurantRepo restaurantRepository;

    @Autowired
    private WorkingHourMapper workingHourMapper;

    @Override
    public List<WorkingHourDTO> getAllWorkingHours(Long restaurantId) {
        List<WorkingHour> hours = workingHourRepository.findByRestaurantId(restaurantId);
        return workingHourMapper.toDTO(hours);
    }

    @Override
    public WorkingHourDTO addWorkingHours(WorkingHourDTO workingHourDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementExistsException("Restaurant not found with id: " + restaurantId));
        WorkingHour hour = workingHourMapper.toEntity(workingHourDTO);
        hour.setRestaurant(restaurant);
        WorkingHour savedHour = workingHourRepository.save(hour);
        return workingHourMapper.toDTO(savedHour);
    }

    @Override
    public WorkingHourDTO updateWorkingHours(Long hourId, WorkingHourDTO workingHourDTO) {
        WorkingHour hour = workingHourRepository.findById(hourId)
                .orElseThrow(() -> new NoSuchElementExistsException("Working hours not found with id: " + hourId));
        hour.setDayOfWeek(workingHourDTO.getDayOfWeek());
        hour.setClosingTime(workingHourDTO.getClosingTime());
        hour.setDayOfWeek(workingHourDTO.getDayOfWeek());
        WorkingHour updatedHour = workingHourRepository.save(hour);
        return workingHourMapper.toDTO(updatedHour);
    }

    @Override
    public void deleteWorkingHours(Long hourId) {
        if (!workingHourRepository.existsById(hourId)) {
            throw new NoSuchElementExistsException("Working hours not found with id: " + hourId);
        }
        workingHourRepository.deleteById(hourId);
        logger.info("Deleted working hours with id: {}", hourId);
    }
}
