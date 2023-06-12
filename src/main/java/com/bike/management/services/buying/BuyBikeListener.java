package com.bike.management.services.buying;

import com.bike.common.BikeDto;
import com.bike.common.events.BuyBikeEvent;
import com.bike.common.events.NewInventoryEvent;
import com.bike.management.config.JmsConfig;
import com.bike.management.domain.Bike;
import com.bike.management.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyBikeListener {

    private final BikeRepository bikeRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BUY_BIKE_REQUEST)
    public void listen(BuyBikeEvent event) {
        BikeDto bikeDto = event.getBikeDto();

        Bike bike = bikeRepository.getOne(bikeDto.getId());

        bikeDto.setQuantity(bike.getQuantity());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(bikeDto);

        log.debug("Brewed bike " + bike.getMinOnHand() + " : QOH: " + bikeDto.getQuantity());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
    }
}
