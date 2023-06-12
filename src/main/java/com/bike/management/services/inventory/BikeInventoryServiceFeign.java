package com.bike.management.services.inventory;

import com.bike.management.services.inventory.model.BikeInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local-discovery")
@Service
public class BikeInventoryServiceFeign implements BikeInventoryService {

    public static final String INVENTORY_PATH = "/api/v1/bike/{bikeId}/inventory";

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(UUID bikeId) {
        log.debug("Calling Inventory Service - BeerId: " + bikeId);

        ResponseEntity<List<BikeInventoryDto>> responseEntity = inventoryServiceFeignClient.getOnhandInventory(bikeId);

        Integer onHand = Objects.requireNonNull(responseEntity.getBody())
                .stream()
                .mapToInt(BikeInventoryDto::getQuantity)
                .sum();

        log.debug("BeerId: " + bikeId + " On hand is: " + onHand);

        return onHand;
    }
}
