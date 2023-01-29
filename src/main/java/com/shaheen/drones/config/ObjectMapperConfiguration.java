package com.shaheen.drones.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static com.shaheen.drones.config.DroneResponseBodyFieldsFilter.JSON_FILTER;

@Configuration
public class ObjectMapperConfiguration {

  @Primary
  @Bean
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
    objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE, true);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider().addFilter(JSON_FILTER, SimpleBeanPropertyFilter.serializeAll());
    objectMapper.setFilterProvider(simpleFilterProvider);
    return objectMapper;
  }
}
