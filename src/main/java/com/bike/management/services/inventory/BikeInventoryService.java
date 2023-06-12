package com.bike.management.services.inventory;

import java.util.UUID;

public interface BikeInventoryService {
    Integer getOnhandInventory(UUID bikeId);
}
