package com.bike.management.web.controllers;

import com.bike.common.BikeDto;
import com.bike.common.events.BikeTypeEnum;
import com.bike.management.bootstrap.BikeLoader;
import com.bike.management.services.BikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BikeController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class BikeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BikeService bikeService;

    @Test
    void getBikeById() throws Exception {

        given(bikeService.getById(any(), anyBoolean())).willReturn(getValidBikeDto());

        mockMvc.perform(get("/api/v1/bike/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    void saveNewBike() throws Exception {

        BikeDto bikeDto = getValidBikeDto();
        String bikeDtoJson = objectMapper.writeValueAsString(bikeDto);

        given(bikeService.saveNewBike(any())).willReturn(getValidBikeDto());

        mockMvc.perform(post("/api/v1/bike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bikeDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBikeById() throws Exception {
        given(bikeService.updateBike(any(), any())).willReturn(getValidBikeDto());

        BikeDto bikeDto = getValidBikeDto();
        String bikeDtoJson = objectMapper.writeValueAsString(bikeDto);

        mockMvc.perform(put("/api/v1/bike/" + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bikeDtoJson))
                .andExpect(status().isNoContent());
    }

    BikeDto getValidBikeDto() {
        return BikeDto.builder()
                .bikeName("My Bike")
                .bikeType(BikeTypeEnum.ROAD.toString())
                .price(new BigDecimal("100.99"))
                .upc(BikeLoader.BIKE_1_UPC)
                .build();
    }
}
