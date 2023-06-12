package com.bike.management.services.inventory;

import com.bike.management.config.FeignClientConfig;
import com.bike.management.services.inventory.model.BikeInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "bike-inventory-service", fallback = InventoryServiceFeignClientFailover.class, configuration = FeignClientConfig.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = BikeInventoryServiceFeign.INVENTORY_PATH)
    ResponseEntity<List<BikeInventoryDto>> getOnhandInventory(@PathVariable UUID bikeId);
}
