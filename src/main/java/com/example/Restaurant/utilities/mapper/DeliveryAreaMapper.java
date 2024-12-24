package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.DeliveryAreaDTO;
import com.example.Restaurant.model.DeliveryArea;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryAreaMapper {

    DeliveryAreaDTO toDTO(DeliveryArea deliveryArea);

    DeliveryArea toEntity(DeliveryAreaDTO deliveryAreaDTO);

    List<DeliveryAreaDTO> toDTO(List<DeliveryArea> deliveryAreas);

    List<DeliveryArea> toEntity(List<DeliveryAreaDTO> deliveryAreaDTOs);
}
