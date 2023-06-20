package com.bike.management.services.buying;

import com.bike.common.events.BuyBikeEvent;
import com.bike.management.config.JmsConfig;
import com.bike.management.domain.Bike;
import com.bike.management.repository.BikeRepository;
import com.bike.management.services.inventory.BikeInventoryService;
import com.bike.management.web.mappers.BikeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuyingService {
    private final BikeRepository bikeRepository;
    private final BikeInventoryService bikeInventoryService;
    private final JmsTemplate jmsTemplate;
    private final BikeMapper bikeMapper;

    @Scheduled(fixedRate = 5000) //every 5 seconds
    public void checkForLowInventory() {
        List<Bike> bikes = bikeRepository.findAll();

        bikes.forEach(bike -> {
            Integer invQOH = bikeInventoryService.getOnhandInventory(bike.getId());
            log.info("Checking Inventory for: " + bike.getBikeName() + " / " + bike.getId());
            log.info("Min Onhand is: " + bike.getMinOnHand());
            log.info("Inventory is: " + invQOH);

            if (bike.getMinOnHand() >= invQOH) {
                jmsTemplate.convertAndSend(JmsConfig.BUY_BIKE_REQUEST, new BuyBikeEvent(bikeMapper.bikeToBikeDto(bike)));
            }
        });

    }
}
