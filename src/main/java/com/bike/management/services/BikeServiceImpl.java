package com.bike.management.services;

import com.bike.common.BikeDto;
import com.bike.common.BikePagedList;
import com.bike.common.events.BikeTypeEnum;
import com.bike.management.domain.Bike;
import com.bike.management.repository.BikeRepository;
import com.bike.management.web.controllers.NotFoundException;
import com.bike.management.web.mappers.BikeMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BikeServiceImpl implements BikeService {
    private final BikeRepository bikeRepository;
    private final BikeMapper bikeMapper;

    @Cacheable(cacheNames = "bikeListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BikePagedList listBikes(String bikeName, BikeTypeEnum bikeType, PageRequest pageRequest, Boolean showInventoryOnHand) {

        BikePagedList bikePagedList;
        Page<Bike> bikePage;

        if (!StringUtils.isEmpty(bikeName) && !StringUtils.isEmpty(bikeType.toString())) {
            //search both
            bikePage = bikeRepository.findAllByBikeNameAndBikeType(bikeName, bikeType, pageRequest);
        } else if (!StringUtils.isEmpty(bikeName) && StringUtils.isEmpty(bikeType.toString())) {
            //search bike_service name
            bikePage = bikeRepository.findAllByBikeName(bikeName, pageRequest);
        } else if (StringUtils.isEmpty(bikeName) && !StringUtils.isEmpty(bikeType.toString())) {
            //search bike_service style
            bikePage = bikeRepository.findAllByBikeType(bikeType, pageRequest);
        } else {
            bikePage = bikeRepository.findAll(pageRequest);
        }

        if (showInventoryOnHand) {
            bikePagedList = new BikePagedList(bikePage
                    .getContent()
                    .stream()
                    .map(bikeMapper::bikeToBikeDtoWithInventory)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(bikePage.getPageable().getPageNumber(),
                                    bikePage.getPageable().getPageSize()),
                    bikePage.getTotalElements());
        } else {
            bikePagedList = new BikePagedList(bikePage
                    .getContent()
                    .stream()
                    .map(bikeMapper::bikeToBikeDto)
                    .collect(Collectors.toList()),
                    PageRequest
                            .of(bikePage.getPageable().getPageNumber(),
                                    bikePage.getPageable().getPageSize()),
                    bikePage.getTotalElements());
        }

        return bikePagedList;
    }

    @Cacheable(cacheNames = "bikeCache", key = "#bikeId", condition = "#showInventoryOnHand == false ")
    @Override
    public BikeDto getById(UUID bikeId, Boolean showInventoryOnHand) {
        if (showInventoryOnHand) {
            return bikeMapper.bikeToBikeDtoWithInventory(
                    bikeRepository.findById(bikeId).orElseThrow(NotFoundException::new)
            );
        } else {
            return bikeMapper.bikeToBikeDto(
                    bikeRepository.findById(bikeId).orElseThrow(NotFoundException::new)
            );
        }
    }

    @Override
    public BikeDto saveNewBike(BikeDto bikeDto) {
        return bikeMapper.bikeToBikeDto(bikeRepository.save(bikeMapper.bikeDtoToBike(bikeDto)));
    }

    @Override
    public BikeDto updateBike(UUID bikeId, BikeDto bikeDto) {
        Bike bike = bikeRepository.findById(bikeId).orElseThrow(NotFoundException::new);

        bike.setBikeName(bikeDto.getBikeName());
        bike.setBikeType(bikeDto.getBikeType().toString());
        bike.setPrice(bikeDto.getPrice());
        bike.setUpc(bikeDto.getUpc());

        return bikeMapper.bikeToBikeDto(bikeRepository.save(bike));
    }

    @Cacheable(cacheNames = "bikeUpcCache")
    @Override
    public BikeDto getByUpc(String upc) {
        return bikeMapper.bikeToBikeDto(bikeRepository.findByUpc(upc));
    }
}
