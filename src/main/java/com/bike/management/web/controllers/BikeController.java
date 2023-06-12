package com.bike.management.web.controllers;

import com.bike.common.BikeDto;
import com.bike.common.BikePagedList;
import com.bike.common.events.BikeTypeEnum;
import com.bike.management.services.BikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BikeController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BikeService bikeService;

    @GetMapping(produces = {"application/json"}, path = "bike")
    public ResponseEntity<BikePagedList> listBikes(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "bikeName", required = false) String bikeName,
                                                   @RequestParam(value = "bikeType", required = false) BikeTypeEnum bikeType,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {

        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BikePagedList bikeList = bikeService.listBikes(bikeName, bikeType, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);

        return new ResponseEntity<>(bikeList, HttpStatus.OK);
    }

    @GetMapping("bike/{bikeId}")
    public ResponseEntity<BikeDto> getBikeById(@PathVariable("bikeId") UUID bikeId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        if (showInventoryOnHand == null) {
            showInventoryOnHand = false;
        }

        return new ResponseEntity<>(bikeService.getById(bikeId, showInventoryOnHand), HttpStatus.OK);
    }

    @GetMapping("bikeUpc/{upc}")
    public ResponseEntity<BikeDto> getBikeByUpc(@PathVariable("upc") String upc) {
        return new ResponseEntity<>(bikeService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "bike")
    public ResponseEntity saveNewBike(@RequestBody @Validated BikeDto bikeDto) {
        return new ResponseEntity<>(bikeService.saveNewBike(bikeDto), HttpStatus.CREATED);
    }

    @PutMapping("bike/{bikeId}")
    public ResponseEntity updateBikeById(@PathVariable("bikeId") UUID bikeId, @RequestBody @Validated BikeDto bikeDto) {
        return new ResponseEntity<>(bikeService.updateBike(bikeId, bikeDto), HttpStatus.NO_CONTENT);
    }

}
