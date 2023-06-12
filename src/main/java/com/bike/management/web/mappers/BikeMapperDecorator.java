package com.bike.management.web.mappers;

import com.bike.common.BikeDto;
import com.bike.management.domain.Bike;
import com.bike.management.services.inventory.BikeInventoryService;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BikeMapperDecorator implements BikeMapper {
    private BikeInventoryService bikeInventoryService;
    private BikeMapper mapper;

    @Autowired
    public void setBikeInventoryService(BikeInventoryService bikeInventoryService) {
        this.bikeInventoryService = bikeInventoryService;
    }

    @Autowired
    public void setMapper(BikeMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public BikeDto bikeToBikeDto(Bike bike) {
        return mapper.bikeToBikeDto(bike);
    }

    @Override
    public BikeDto bikeToBikeDtoWithInventory(Bike bike) {
        BikeDto dto = mapper.bikeToBikeDto(bike);
        dto.setQuantity(bikeInventoryService.getOnhandInventory(bike.getId()));
        return dto;
    }

    @Override
    public Bike bikeDtoToBike(BikeDto bikeDto) {
        return mapper.bikeDtoToBike(bikeDto);
    }
}
