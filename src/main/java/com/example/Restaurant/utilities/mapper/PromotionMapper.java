package com.example.Restaurant.utilities.mapper;

import com.example.Restaurant.dto.PromotionDTO;
import com.example.Restaurant.model.Promotion;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PromotionMapper {

    PromotionDTO toDTO(Promotion promotion);

    Promotion toEntity(PromotionDTO promotionDTO);

    List<PromotionDTO> toDTO(List<Promotion> promotions);

    List<Promotion> toEntity(List<PromotionDTO> promotionDTOs);
}

