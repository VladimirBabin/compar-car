package com.comparcar.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValuePropertyMappingStrategy;


@MapperConfig(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MapstructConfig {
    
}
