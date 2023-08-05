package com.backend.core.entities.requestdto.invoice;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class InvoiceFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    InvoiceFilterDTO filter;

    @JsonProperty("pagination")
    PaginationDTO pagination;


    public InvoiceFilterRequestDTO(InvoiceFilterDTO filter, PaginationDTO pagination) {
        this.filter = filter;
        this.pagination = pagination;
    }

    public InvoiceFilterRequestDTO() {}


    @Override
    public Object getFilter() {
        return this.filter;
    }

    @Override
    public PaginationDTO getPagination() {
        return this.pagination;
    }

    @Override
    public void setFilter(Object filter) {
        LinkedHashMap<String, Object> filterMap = (LinkedHashMap<String, Object>) filter;
        ObjectMapper objectMapper = new ObjectMapper();
        this.filter = objectMapper.convertValue(filterMap, InvoiceFilterDTO.class);
    }

    @Override
    public void setPagination(PaginationDTO pagination) {
        this.pagination = pagination;
    }
}
