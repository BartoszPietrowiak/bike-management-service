package com.bike.management.services.inventory;

import com.bike.management.services.inventory.model.BikeInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient {

    private final InventoryFailoverFeignClient failoverFeignClient;

    @Override
    public ResponseEntity<List<BikeInventoryDto>> getOnhandInventory(UUID bikeId) {
        return failoverFeignClient.getOnhandInventory();
    }
}
