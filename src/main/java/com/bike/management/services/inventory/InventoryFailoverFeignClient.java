package com.bike.management.services.inventory;

import com.bike.management.services.inventory.model.BikeInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "bike-inventory-failover")
public interface InventoryFailoverFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/bike-inventory-failover")
    ResponseEntity<List<BikeInventoryDto>> getOnhandInventory();
}
