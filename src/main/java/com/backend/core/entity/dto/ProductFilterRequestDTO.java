package com.backend.core.entity.dto;

import com.backend.core.entity.interfaces.FilterRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ProductFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    ProductFilterDTO filter;

    @JsonProperty("key")
    String key;


    public ProductFilterRequestDTO(ProductFilterDTO filter, String key) {
        this.filter = filter;
        this.key = key;
    }

    public ProductFilterRequestDTO() {}

    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public void setFilter(Object filter) throws JsonProcessingException {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, ProductFilterDTO.class);;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }
}
