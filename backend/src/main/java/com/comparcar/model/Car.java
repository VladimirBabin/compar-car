package com.comparcar.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.Year;

@Getter
@Setter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Car {
    @EqualsAndHashCode.Include
    private Long id;
    private String model;
    private Integer manufacturingYear;
    private BigDecimal engineVolume;
    private BodyType bodyType;
    private FuelType fuelType;
    private Integer trunkSize;
    private BigDecimal fuelConsumption;
    private BigDecimal averageServicePrice;
    private BigDecimal price;
    private Integer mileage;

    // Business logic methods
    public boolean isNewCar() {
        return manufacturingYear != null && manufacturingYear >= Year.now().getValue() - 1;
    }

    public boolean isElectric() {
        return fuelType == FuelType.ELECTRIC;
    }

    public boolean isHybrid() {
        return fuelType == FuelType.HYBRID || fuelType == FuelType.PLUG_IN_HYBRID;
    }

    public boolean isEcoFriendly() {
        return isElectric() || isHybrid() || fuelType == FuelType.HYDROGEN;
    }

    public BigDecimal getTotalCostOfOwnership() {
        if (price == null || averageServicePrice == null) {
            return BigDecimal.ZERO;
        }
        // Simple calculation: price + 5 years of service costs
        return price.add(averageServicePrice.multiply(BigDecimal.valueOf(5)));
    }

    public boolean isGoodValueForMoney() {
        if (price == null || averageServicePrice == null) {
            return false;
        }
        // Consider it good value if service cost is less than 10% of car price
        return averageServicePrice.compareTo(price.multiply(BigDecimal.valueOf(0.1))) < 0;
    }

    public boolean isFuelEfficient() {
        if (fuelConsumption == null) {
            return false;
        }
        // Consider fuel efficient if consumption is less than 6L/100km
        return fuelConsumption.compareTo(BigDecimal.valueOf(6.0)) < 0;
    }

    public boolean isSpacious() {
        if (trunkSize == null) {
            return false;
        }
        // Consider spacious if trunk size is more than 500L
        return trunkSize > 500;
    }

    public boolean isRecentModel() {
        if (manufacturingYear == null) {
            return false;
        }
        // Consider recent if manufactured in the last 5 years
        return manufacturingYear >= Year.now().getValue() - 5;
    }

    public boolean isHighMileage() {
        if (mileage == null) {
            return false;
        }
        // Consider high mileage if more than 100,000 km
        return mileage > 100000;
    }

    public boolean isLowMileage() {
        if (mileage == null) {
            return false;
        }
        // Consider low mileage if less than 50,000 km
        return mileage < 50000;
    }

    public boolean isValidForComparison() {
        return model != null && !model.trim().isEmpty() &&
               manufacturingYear != null && manufacturingYear >= 1900 && manufacturingYear <= Year.now().getValue() &&
               engineVolume != null && engineVolume.compareTo(BigDecimal.valueOf(0.5)) >= 0 && engineVolume.compareTo(BigDecimal.valueOf(10.0)) <= 0 &&
               bodyType != null &&
               fuelType != null &&
               trunkSize != null && trunkSize >= 100 && trunkSize <= 3000 &&
               fuelConsumption != null && fuelConsumption.compareTo(BigDecimal.valueOf(1.0)) >= 0 && fuelConsumption.compareTo(BigDecimal.valueOf(30.0)) <= 0 &&
               averageServicePrice != null && averageServicePrice.compareTo(BigDecimal.ZERO) >= 0 && averageServicePrice.compareTo(BigDecimal.valueOf(10000.0)) <= 0 &&
               price != null && price.compareTo(BigDecimal.valueOf(100.0)) >= 0 && price.compareTo(BigDecimal.valueOf(1000000.0)) <= 0 &&
               mileage != null && mileage >= 0 && mileage <= 1000000;
    }
} 