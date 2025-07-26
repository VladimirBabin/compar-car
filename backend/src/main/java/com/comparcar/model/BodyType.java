package com.comparcar.model;

public enum BodyType {
    SEDAN("Sedan"),
    HATCHBACK("Hatchback"),
    STATION_WAGON("Station Wagon"),
    CROSSOVER("Crossover"),
    SUV("SUV"),
    COUPE("Coupe"),
    CONVERTIBLE("Convertible"),
    MINIVAN("Minivan"),
    PICKUP("Pickup"),
    VAN("Van"),
    WAGON("Wagon"),
    LIFTBACK("Liftback"),
    FASTBACK("Fastback"),
    ROADSTER("Roadster"),
    OTHER("Other");

    private final String displayName;

    BodyType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
} 