package com.comparcar.mapper;

import com.comparcar.model.Car;
import com.comparcar.model.CarEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import com.comparcar.config.MapstructConfig;

@Mapper(config = MapstructConfig.class)
public interface CarEntityMapper {

    @Mapping(target = "id", ignore = true)
    CarEntity toEntity(Car car);

    Car toDomain(CarEntity entity);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDomain(@MappingTarget CarEntity entity, Car car);
} 
