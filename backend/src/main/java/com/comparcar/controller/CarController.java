package com.comparcar.controller;

import com.comparcar.dto.CarDto;
import com.comparcar.dto.CarFilterDto;
import com.comparcar.mapper.CarDtoMapper;
import com.comparcar.model.BodyType;
import com.comparcar.model.Car;
import com.comparcar.model.FuelType;
import com.comparcar.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class CarController {
    
    private final CarService carService;
    private final CarDtoMapper carDtoMapper;
    
    @PostMapping
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CarDto carDto) {
        Car car = carDtoMapper.toDomain(carDto);
        Car savedCar = carService.createCar(car);
        CarDto createdCarDto = carDtoMapper.toDto(savedCar);
        return new ResponseEntity<>(createdCarDto, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        Optional<Car> car = carService.getCarById(id);
        return car.map(carModel -> {
            CarDto carDto = carDtoMapper.toDto(carModel);
            return new ResponseEntity<>(carDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CarDto> updateCar(@PathVariable Long id, @Valid @RequestBody CarDto carDto) {
        try {
            Car car = carDtoMapper.toDomain(carDto);
            Car updatedCar = carService.updateCar(id, car);
            CarDto updatedCarDto = carDtoMapper.toDto(updatedCar);
            return new ResponseEntity<>(updatedCarDto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        try {
            carService.deleteCar(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    public ResponseEntity<Page<CarDto>> getCarsWithFilters(
            @RequestParam(required = false) String model,
            @RequestParam(required = false) Integer manufacturingYearFrom,
            @RequestParam(required = false) Integer manufacturingYearTo,
            @RequestParam(required = false) Double engineVolumeFrom,
            @RequestParam(required = false) Double engineVolumeTo,
            @RequestParam(required = false) BodyType bodyType,
            @RequestParam(required = false) FuelType fuelType,
            @RequestParam(required = false) Integer trunkSizeFrom,
            @RequestParam(required = false) Integer trunkSizeTo,
            @RequestParam(required = false) Double fuelConsumptionFrom,
            @RequestParam(required = false) Double fuelConsumptionTo,
            @RequestParam(required = false) Double averageServicePriceFrom,
            @RequestParam(required = false) Double averageServicePriceTo,
            @RequestParam(required = false) Double priceFrom,
            @RequestParam(required = false) Double priceTo,
            @RequestParam(required = false) Integer mileageFrom,
            @RequestParam(required = false) Integer mileageTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        
        CarFilterDto filterDto = new CarFilterDto();
        filterDto.setModel(model);
        filterDto.setManufacturingYearFrom(manufacturingYearFrom);
        filterDto.setManufacturingYearTo(manufacturingYearTo);
        filterDto.setEngineVolumeFrom(engineVolumeFrom);
        filterDto.setEngineVolumeTo(engineVolumeTo);
        filterDto.setBodyType(bodyType);
        filterDto.setFuelType(fuelType);
        filterDto.setTrunkSizeFrom(trunkSizeFrom);
        filterDto.setTrunkSizeTo(trunkSizeTo);
        filterDto.setFuelConsumptionFrom(fuelConsumptionFrom);
        filterDto.setFuelConsumptionTo(fuelConsumptionTo);
        filterDto.setAverageServicePriceFrom(averageServicePriceFrom);
        filterDto.setAverageServicePriceTo(averageServicePriceTo);
        filterDto.setPriceFrom(priceFrom);
        filterDto.setPriceTo(priceTo);
        filterDto.setMileageFrom(mileageFrom);
        filterDto.setMileageTo(mileageTo);
        filterDto.setPage(page);
        filterDto.setSize(size);
        filterDto.setSortBy(sortBy);
        filterDto.setSortDirection(Sort.Direction.fromString(sortDirection));
        
        Page<Car> cars = carService.getCarsWithFilters(filterDto);
        Page<CarDto> carDtos = cars.map(carDtoMapper::toDto);
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<CarDto>> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDto> carDtos = cars.stream()
                .map(carDtoMapper::toDto)
                .toList();
        return new ResponseEntity<>(carDtos, HttpStatus.OK);
    }
    
    @GetMapping("/body-types")
    public ResponseEntity<List<BodyType>> getBodyTypes() {
        List<BodyType> bodyTypes = Arrays.asList(BodyType.values());
        return new ResponseEntity<>(bodyTypes, HttpStatus.OK);
    }
    
    @GetMapping("/fuel-types")
    public ResponseEntity<List<FuelType>> getFuelTypes() {
        List<FuelType> fuelTypes = Arrays.asList(FuelType.values());
        return new ResponseEntity<>(fuelTypes, HttpStatus.OK);
    }
} 