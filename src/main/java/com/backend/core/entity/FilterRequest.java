package com.backend.core.entity;

import com.backend.core.entity.api.PaginationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    Object getFilter();

    void setFilter(Object filter) throws JsonProcessingException;

    PaginationDTO getPagination();

    void setPagination(PaginationDTO paginationDTO);
}
