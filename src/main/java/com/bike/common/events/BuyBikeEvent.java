package com.bike.common.events;

import com.bike.common.BikeDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BuyBikeEvent extends BikeEvent {

    public BuyBikeEvent(BikeDto bikeDto) {
        super(bikeDto);
    }
}
