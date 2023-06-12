package com.bike.management.services.order;

import com.bike.common.BikeOrderDto;
import com.bike.management.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BikeOrderValidator {

    private final BikeRepository bikeRepository;

    public Boolean validateOrder(BikeOrderDto bikeOrder){

        AtomicInteger bikesNotFound = new AtomicInteger();

        bikeOrder.getBikeOrderLines().forEach(orderline -> {
            if(bikeRepository.findByUpc(orderline.getUpc()) == null){
                bikesNotFound.incrementAndGet();
            }
        });

        return bikesNotFound.get() == 0;
    }

}
