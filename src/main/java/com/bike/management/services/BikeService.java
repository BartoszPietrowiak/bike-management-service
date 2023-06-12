package com.bike.management.services;

import com.bike.common.BikeDto;
import com.bike.common.BikePagedList;
import com.bike.common.events.BikeTypeEnum;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BikeService {
    BikePagedList listBikes(String bikeName, BikeTypeEnum bikeStyle, PageRequest pageRequest, Boolean showInventoryOnHand);

    BikeDto getById(UUID bikeId, Boolean showInventoryOnHand);

    BikeDto saveNewBike(BikeDto bikeDto);

    BikeDto updateBike(UUID bikeId, BikeDto bikeDto);

    BikeDto getByUpc(String upc);
}
