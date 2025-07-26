package com.comparcar.service;

import com.comparcar.dto.CarDto;
import com.comparcar.dto.CarFilterDto;
import com.comparcar.mapper.CarEntityMapper;
import com.comparcar.model.Car;
import com.comparcar.model.CarEntity;
import com.comparcar.repository.CarRepository;
import com.comparcar.specification.CarSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {
    
    private final CarRepository carRepository;
    private final CarEntityMapper carEntityMapper;
    
    public Car createCar(Car car) {
        // Validate domain model
        if (!car.isValidForComparison()) {
            throw new IllegalArgumentException("Invalid car data for comparison");
        }
        
        CarEntity carEntity = carEntityMapper.toEntity(car);
        CarEntity savedEntity = carRepository.save(carEntity);
        return carEntityMapper.toDomain(savedEntity);
    }
    
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id)
                .map(carEntityMapper::toDomain);
    }
    
    public Car updateCar(Long id, Car car) {
        Optional<CarEntity> existingEntity = carRepository.findById(id);
        if (existingEntity.isPresent()) {
            CarEntity entity = existingEntity.get();
            
            // Validate domain model
            if (!car.isValidForComparison()) {
                throw new IllegalArgumentException("Invalid car data for comparison");
            }
            
            // Update entity from domain
            carEntityMapper.updateEntityFromDomain(entity, car);
            CarEntity savedEntity = carRepository.save(entity);
            return carEntityMapper.toDomain(savedEntity);
        }
        throw new RuntimeException("Car not found with id: " + id);
    }
    
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }
    
    public Page<Car> getCarsWithFilters(CarFilterDto filterDto) {
        // Create pageable with sorting
        Sort sort = Sort.by(filterDto.getSortDirection(), filterDto.getSortBy());
        Pageable pageable = PageRequest.of(filterDto.getPage(), filterDto.getSize(), sort);
        
        // Create specification for filtering
        var specification = CarSpecification.withFilters(filterDto);
        
        // Execute query with specification and pagination
        Page<CarEntity> carEntityPage = carRepository.findAll(specification, pageable);
        
        // Convert to domain models
        return carEntityPage.map(carEntityMapper::toDomain);
    }
    
    public List<Car> getAllCars() {
        return carRepository.findAll().stream()
                .map(carEntityMapper::toDomain)
                .toList();
    }
} 