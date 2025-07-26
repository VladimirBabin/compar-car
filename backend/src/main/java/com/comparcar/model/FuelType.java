package com.comparcar.model;

public enum FuelType {
    GASOLINE("Gasoline"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric"),
    PLUG_IN_HYBRID("Plug-in Hybrid"),
    HYDROGEN("Hydrogen"),
    LPG("LPG"),
    CNG("CNG"),
    ETHANOL("Ethanol"),
    BIODIESEL("Biodiesel"),
    OTHER("Other");

    private final String displayName;

    FuelType(String displayName) {
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