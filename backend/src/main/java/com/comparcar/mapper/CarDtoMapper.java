package com.comparcar.mapper;

import com.comparcar.dto.CarDto;
import com.comparcar.model.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import com.comparcar.config.MapstructConfig;

@Mapper(config = MapstructConfig.class)
public interface CarDtoMapper {

    CarDto toDto(Car car);

    Car toDomain(CarDto dto);

    void updateDomainFromDto(@MappingTarget Car car, CarDto dto);
} 
