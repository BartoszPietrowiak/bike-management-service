package com.bike.common.events;

import com.bike.common.BikeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BikeEvent implements Serializable {

    static final long serialVersionUID = -5781515597148163111L;

    private BikeDto bikeDto;
}
