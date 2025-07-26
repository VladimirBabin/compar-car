package com.comparcar.specification;

import com.comparcar.dto.CarFilterDto;
import com.comparcar.model.CarEntity;
import com.comparcar.model.BodyType;
import com.comparcar.model.FuelType;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class CarSpecification {
    
    public static Specification<CarEntity> withFilters(CarFilterDto filterDto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            // Model filter (case-insensitive like)
            if (filterDto.getModel() != null && !filterDto.getModel().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("model")),
                    "%" + filterDto.getModel().toLowerCase() + "%"
                ));
            }
            
            // Manufacturing year range
            if (filterDto.getManufacturingYearFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("manufacturingYear"), 
                    filterDto.getManufacturingYearFrom()
                ));
            }
            
            if (filterDto.getManufacturingYearTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("manufacturingYear"), 
                    filterDto.getManufacturingYearTo()
                ));
            }
            
            // Engine volume range
            if (filterDto.getEngineVolumeFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("engineVolume"), 
                    filterDto.getEngineVolumeFrom()
                ));
            }
            
            if (filterDto.getEngineVolumeTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("engineVolume"), 
                    filterDto.getEngineVolumeTo()
                ));
            }
            
            // Body type filter
            if (filterDto.getBodyType() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("bodyType"), 
                    filterDto.getBodyType()
                ));
            }
            
            // Fuel type filter
            if (filterDto.getFuelType() != null) {
                predicates.add(criteriaBuilder.equal(
                    root.get("fuelType"), 
                    filterDto.getFuelType()
                ));
            }
            
            // Trunk size range
            if (filterDto.getTrunkSizeFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("trunkSize"), 
                    filterDto.getTrunkSizeFrom()
                ));
            }
            
            if (filterDto.getTrunkSizeTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("trunkSize"), 
                    filterDto.getTrunkSizeTo()
                ));
            }
            
            // Fuel consumption range
            if (filterDto.getFuelConsumptionFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("fuelConsumption"), 
                    filterDto.getFuelConsumptionFrom()
                ));
            }
            
            if (filterDto.getFuelConsumptionTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("fuelConsumption"), 
                    filterDto.getFuelConsumptionTo()
                ));
            }
            
            // Average service price range
            if (filterDto.getAverageServicePriceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("averageServicePrice"), 
                    filterDto.getAverageServicePriceFrom()
                ));
            }
            
            if (filterDto.getAverageServicePriceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("averageServicePrice"), 
                    filterDto.getAverageServicePriceTo()
                ));
            }
            
            // Price range
            if (filterDto.getPriceFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("price"), 
                    filterDto.getPriceFrom()
                ));
            }
            
            if (filterDto.getPriceTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("price"), 
                    filterDto.getPriceTo()
                ));
            }
            
            // Mileage range
            if (filterDto.getMileageFrom() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("mileage"), 
                    filterDto.getMileageFrom()
                ));
            }
            
            if (filterDto.getMileageTo() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("mileage"), 
                    filterDto.getMileageTo()
                ));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
} 