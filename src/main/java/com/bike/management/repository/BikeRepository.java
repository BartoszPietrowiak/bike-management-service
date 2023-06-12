package com.bike.management.repository;

import com.bike.common.events.BikeTypeEnum;
import com.bike.management.domain.Bike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BikeRepository extends JpaRepository<Bike, UUID> {
    Page<Bike> findAllByBikeName(String bikeName, Pageable pageable);

    Page<Bike> findAllByBikeType(BikeTypeEnum bikeType, Pageable pageable);

    Page<Bike> findAllByBikeNameAndBikeType(String bikeName, BikeTypeEnum bikeType, Pageable pageable);

    Bike findByUpc(String upc);
}
