package com.comparcar.dto;

import com.comparcar.model.BodyType;
import com.comparcar.model.FuelType;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class CarFilterDto {
    
    private String model;
    private Integer manufacturingYearFrom;
    private Integer manufacturingYearTo;
    private Double engineVolumeFrom;
    private Double engineVolumeTo;
    private BodyType bodyType;
    private FuelType fuelType;
    private Integer trunkSizeFrom;
    private Integer trunkSizeTo;
    private Double fuelConsumptionFrom;
    private Double fuelConsumptionTo;
    private Double averageServicePriceFrom;
    private Double averageServicePriceTo;
    private Double priceFrom;
    private Double priceTo;
    private Integer mileageFrom;
    private Integer mileageTo;
    
    // Pagination
    private int page = 0;
    private int size = 20;
    private String sortBy = "id";
    private Sort.Direction sortDirection = Sort.Direction.ASC;
} 