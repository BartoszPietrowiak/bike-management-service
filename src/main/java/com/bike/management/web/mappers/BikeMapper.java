package com.bike.management.web.mappers;

import com.bike.common.BikeDto;
import com.bike.management.domain.Bike;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(uses = {DateMapper.class})
@DecoratedWith(BikeMapperDecorator.class)
public interface BikeMapper {

    BikeDto bikeToBikeDto(Bike bike);

    BikeDto bikeToBikeDtoWithInventory(Bike bike);

    Bike bikeDtoToBike(BikeDto dto);
}
