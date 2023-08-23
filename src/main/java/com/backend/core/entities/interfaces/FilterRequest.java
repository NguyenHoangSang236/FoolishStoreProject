package com.backend.core.entities.interfaces;

import com.backend.core.entities.requestdto.PaginationDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public interface FilterRequest {
    Object getFilter();

    void setFilter(Object filter) throws JsonProcessingException;

    PaginationDTO getPagination();

    void setPagination(PaginationDTO paginationDTO);
}
