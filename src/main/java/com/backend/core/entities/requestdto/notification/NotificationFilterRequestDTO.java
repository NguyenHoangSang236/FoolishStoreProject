package com.backend.core.entities.requestdto.notification;

import com.backend.core.entities.interfaces.FilterRequest;
import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class NotificationFilterRequestDTO implements FilterRequest {
    @JsonProperty("filter")
    NotificationFilterDTO filter;

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
        this.filter = objectMapper.convertValue(filterMap, NotificationFilterDTO.class);
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