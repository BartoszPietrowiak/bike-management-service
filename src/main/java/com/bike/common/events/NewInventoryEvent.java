package com.bike.common.events;

import com.bike.common.BikeDto;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BikeEvent {
    public NewInventoryEvent(BikeDto bikeDto) {
        super(bikeDto);
    }
}
