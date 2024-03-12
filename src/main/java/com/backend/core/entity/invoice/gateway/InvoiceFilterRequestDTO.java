package com.backend.core.entity.invoice.gateway;

import com.backend.core.entity.FilterRequest;
import com.backend.core.entity.api.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@NoArgsConstructor
@AllArgsConstructor
@Component
public class InvoiceFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    InvoiceFilterDTO filter;

    @JsonProperty("pagination")
    PaginationDTO pagination;


    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public void setFilter(Object filter) {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, InvoiceFilterDTO.class);
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
