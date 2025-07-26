package com.comparcar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Car model is required")
    @Size(min = 1, max = 100, message = "Car model must be between 1 and 100 characters")
    @Column(nullable = false)
    private String model;

    @NotNull(message = "Manufacturing year is required")
    @Min(value = 1900, message = "Manufacturing year must be at least 1900")
    @Max(value = 2030, message = "Manufacturing year cannot be in the future")
    @Column(nullable = false)
    private Integer manufacturingYear;

    @NotNull(message = "Engine volume is required")
    @DecimalMin(value = "0.5", message = "Engine volume must be at least 0.5L")
    @DecimalMax(value = "10.0", message = "Engine volume cannot exceed 10.0L")
    @Column(nullable = false, precision = 3, scale = 1)
    private BigDecimal engineVolume;

    @NotNull(message = "Body type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BodyType bodyType;

    @NotNull(message = "Fuel type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FuelType fuelType;

    @NotNull(message = "Trunk size is required")
    @Min(value = 100, message = "Trunk size must be at least 100L")
    @Max(value = 3000, message = "Trunk size cannot exceed 3000L")
    @Column(nullable = false)
    private Integer trunkSize;

    @NotNull(message = "Fuel consumption is required")
    @DecimalMin(value = "1.0", message = "Fuel consumption must be at least 1.0L/100km")
    @DecimalMax(value = "30.0", message = "Fuel consumption cannot exceed 30.0L/100km")
    @Column(nullable = false, precision = 4, scale = 1)
    private BigDecimal fuelConsumption;

    @NotNull(message = "Average service price is required")
    @DecimalMin(value = "0.0", message = "Average service price cannot be negative")
    @DecimalMax(value = "10000.0", message = "Average service price cannot exceed 10000 EUR")
    @Column(nullable = false, precision = 8, scale = 2)
    private BigDecimal averageServicePrice;

    @NotNull(message = "Car price is required")
    @DecimalMin(value = "100.0", message = "Car price must be at least 100 EUR")
    @DecimalMax(value = "1000000.0", message = "Car price cannot exceed 1000000 EUR")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    @Max(value = 1000000, message = "Mileage cannot exceed 1000000 km")
    @Column(nullable = false)
    private Integer mileage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        CarEntity car = (CarEntity) o;
        return id != null && Objects.equals(id, car.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
} 