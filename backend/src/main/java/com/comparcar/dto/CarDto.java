package com.comparcar.dto;

import com.comparcar.model.BodyType;
import com.comparcar.model.FuelType;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class CarDto {
    
    private Long id;
    
    @NotBlank(message = "Car model is required")
    @Size(min = 1, max = 100, message = "Car model must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s\\-]+$", message = "Car model can only contain letters, numbers, spaces, and hyphens")
    private String model;
    
    @NotNull(message = "Manufacturing year is required")
    @Min(value = 1900, message = "Manufacturing year must be at least 1900")
    @Max(value = 2030, message = "Manufacturing year cannot be in the future")
    private Integer manufacturingYear;
    
    @NotNull(message = "Engine volume is required")
    @DecimalMin(value = "0.5", message = "Engine volume must be at least 0.5L")
    @DecimalMax(value = "10.0", message = "Engine volume cannot exceed 10.0L")
    @Digits(integer = 2, fraction = 1, message = "Engine volume must have at most 2 digits before decimal and 1 after")
    private BigDecimal engineVolume;
    
    @NotNull(message = "Body type is required")
    private BodyType bodyType;
    
    @NotNull(message = "Fuel type is required")
    private FuelType fuelType;
    
    @NotNull(message = "Trunk size is required")
    @Min(value = 100, message = "Trunk size must be at least 100L")
    @Max(value = 3000, message = "Trunk size cannot exceed 3000L")
    private Integer trunkSize;
    
    @NotNull(message = "Fuel consumption is required")
    @DecimalMin(value = "1.0", message = "Fuel consumption must be at least 1.0L/100km")
    @DecimalMax(value = "30.0", message = "Fuel consumption cannot exceed 30.0L/100km")
    @Digits(integer = 2, fraction = 1, message = "Fuel consumption must have at most 2 digits before decimal and 1 after")
    private BigDecimal fuelConsumption;
    
    @NotNull(message = "Average service price is required")
    @DecimalMin(value = "0.0", message = "Average service price cannot be negative")
    @DecimalMax(value = "10000.0", message = "Average service price cannot exceed 10000 EUR")
    @Digits(integer = 5, fraction = 2, message = "Average service price must have at most 5 digits before decimal and 2 after")
    private BigDecimal averageServicePrice;
    
    @NotNull(message = "Car price is required")
    @DecimalMin(value = "100.0", message = "Car price must be at least 100 EUR")
    @DecimalMax(value = "1000000.0", message = "Car price cannot exceed 1000000 EUR")
    @Digits(integer = 7, fraction = 2, message = "Car price must have at most 7 digits before decimal and 2 after")
    private BigDecimal price;
    
    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    @Max(value = 1000000, message = "Mileage cannot exceed 1000000 km")
    private Integer mileage;
} 