package com.bike.management.bootstrap;

import com.bike.common.events.BikeTypeEnum;
import com.bike.management.domain.Bike;
import com.bike.management.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class BikeLoader implements CommandLineRunner {

    public static final String BIKE_1_UPC = "0631234200036";
    public static final String BIKE_2_UPC = "0631234300019";
    public static final String BIKE_3_UPC = "0083783375213";

    private final BikeRepository bikeRepository;

    @Override
    public void run(String... args) throws Exception {

        if (bikeRepository.count() == 0) {
            loadBikeObjects();
        }
    }

    private void loadBikeObjects() {
        Bike b1 = Bike.builder()
                .bikeName("Giant Propel Advanved")
                .bikeType(BikeTypeEnum.ROAD.toString())
                .minOnHand(12)
                .quantity(200)
                .price(new BigDecimal("12.95"))
                .upc(BIKE_1_UPC)
                .build();

        Bike b2 = Bike.builder()
                .bikeName("Superior XT 939")
                .bikeType(BikeTypeEnum.MOUNTAIN.toString())
                .minOnHand(12)
                .quantity(200)
                .price(new BigDecimal("12.95"))
                .upc(BIKE_2_UPC)
                .build();

        Bike b3 = Bike.builder()
                .bikeName("Giant Revoult X")
                .bikeType(BikeTypeEnum.GRAVEL.toString())
                .minOnHand(12)
                .quantity(200)
                .price(new BigDecimal("12.95"))
                .upc(BIKE_3_UPC)
                .build();

        bikeRepository.save(b1);
        bikeRepository.save(b2);
        bikeRepository.save(b3);
    }
}
