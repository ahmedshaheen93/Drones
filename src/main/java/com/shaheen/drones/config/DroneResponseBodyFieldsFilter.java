package com.shaheen.drones.config;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DroneResponseBodyFieldsFilter extends AbstractMappingJacksonResponseBodyAdvice {
  private static final String FILTER_QUERY_PARAM = "fields";
  private static final String DELI = ",";
  public static final String JSON_FILTER = "droneResponseJsonFilter";

  @Override
  protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
    if (bodyContainer.getFilters() != null) {
      return;
    }
    HttpServletRequest baseReq = ((ServletServerHttpRequest) request).getServletRequest();
    String filter = baseReq.getParameter(FILTER_QUERY_PARAM);
    if (!ObjectUtils.isEmpty(filter)) {
      String[] attrs = filter.contains(DELI) ? StringUtils.split(filter, DELI) : new String[]{filter};
      bodyContainer.setFilters(configFilters(attrs));
    }
  }

  private FilterProvider configFilters(String[] filterPropertyNames) {
    return new SimpleFilterProvider().addFilter(JSON_FILTER, SimpleBeanPropertyFilter.filterOutAllExcept(filterPropertyNames));
  }

}
